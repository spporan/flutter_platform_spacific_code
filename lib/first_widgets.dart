
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

typedef FirstWidgetCreatedCallback=void Function(
      FirstWidgetController controller
    );

class FirstWidgetController{
  FirstWidgetController._(int id){
    _channel = MethodChannel('platform_view/first_widgets$id');
    _channel.setMethodCallHandler(_handleMethod);
  }

  MethodChannel _channel;
  var _callBack;
  addListener(void Function (int i) f){
    _callBack=f;
  }

  Future<dynamic> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case 'sendFromNative':
        int count = call.arguments as int;
        print("add :$count");
        _callBack(count);

        return new Future.value("Text from native: $count");
    }
  }
  Future<void> ping() async {
    print("called ping");
    Map<dynamic,dynamic> res=await _channel.invokeMapMethod('ping');
    print("count print:${res['count']}");

  }

  Future<void> showToast(String msg) async {
    print("called showToast");
    Map<dynamic,dynamic> res=await _channel.invokeMapMethod('showToast',msg);
    print("count print:${res['count']}");

  }


}
class FirstWidget extends StatefulWidget{
  const FirstWidget({Key key,this.onFirstWidgetWidgetCreated}):super(key: key);
  final FirstWidgetCreatedCallback onFirstWidgetWidgetCreated;

  @override
  State<StatefulWidget> createState() {
    return _FirstWidgetState();
  }
}
class _FirstWidgetState extends State<FirstWidget>{
  @override
  Widget build(BuildContext context) {
    if(defaultTargetPlatform==TargetPlatform.android){
      return AndroidView(
          viewType: "platform_view/first_widgets",
        onPlatformViewCreated:_onPlatformViewCreated ,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }
    return const Text('iOS platform version is not implemented yet.');
  }

  void _onPlatformViewCreated(int id) {
    if (widget.onFirstWidgetWidgetCreated == null) {
      return;
    }
    widget.onFirstWidgetWidgetCreated(FirstWidgetController._(id));
  }

}