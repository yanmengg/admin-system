### beam-generator
该项目为beam-parent（https://gitee.com/hsshy/beam-parent）的代码生成器。有需要的也阔以拿此项目改造成自己所需要的代码生成器。

### 项目结构
```
├─beam-generator  代码生成器
│        └─resources 
│           ├─mapper   MyBatis文件
│           ├─template 代码生成器模板（可增加或修改相应模板）
│           ├─application.yml    全局配置文件
│           └─generator.properties   代码生成器，配置文件
```

### 使用步骤
- 1、修改generator.properties里的配置，例如包路径、表前缀、作者。
- 2、修改application.yml里的数据库配置。
- 3、运行代码生成器，在浏览器上访问http://localhost:8083/beam-generator/#generator.html
- 4、选中要生成的表、点击生成
- 5、将生成的实体类、dao、service、controller、vue、js放到对应的目录下


### 还有问题请关注公众号，加群或者添加本人微信。
![image.png](http://img.hsshy.cn/upload/20190821/04ce4ff9ddf64816a4831223654d588b.png)