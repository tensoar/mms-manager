# 请求说明

## 校验

除登录等无需校验接口外，其他接口需要携带`token`信息，放在请求的`header`中，`header`名称`X-Access-Token`：
```bash
X-Access-Token: 21a0b845-4dad-451c-8fcb-63fd7936077b
```

## 接口返回格式

接口返回格式如下：
```bash
{
  "code": 1000, # 状态码 1000为成功
  "data": "" # 成功时接口接口
  "msg": "" # 错误时提示信息
}
```
