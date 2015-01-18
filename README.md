### Bracelet Web Readme

JSON Interfase API

URL Example

Find All Data
 - http://ec2-54-169-209-8.ap-southeast-1.compute.amazonaws.com/api/findAll
 
Find By Date (the format pattern is yyyyMMddHHmmss)
 - http://ec2-54-169-209-8.ap-southeast-1.compute.amazonaws.com/api/findByDate/20150112000000

Response Data

```
{"code":"1","datas":[{"id":2,"motionState":"2","pulseState":59,"temperature":36.0,"braceletId":"1234567","createDate":1422720000000}]}
```
- code (Response value: {1: success, 0: failure, -1: error}])
- datas (Array)
