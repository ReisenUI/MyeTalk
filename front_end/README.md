# 《心情说》前端开发README

## 所有的黑色应该是#09181f
#### TODO List
- [x] 主页修改图标
- [ ] 元素间隔、大小
- [ ] 卡片滑动
- [ ] 主页日期下拉栏
- [ ] uni.navigateTo动画
- [x] 天气图标改小
- [ ] 按钮点击动画
- [ ] 关闭页面回弹特效  // pages.json "app-plus":{"bounce":"none"}
- [x] 按钮阴影
- [ ] 字体：思源宋体 CN Heavy、江城斜宋体 700W
- [ ] 天气记录改中文
- [ ] 按钮列表添加type属性，提高代码可读性
- [ ] 心情必须记录两个？
- [x] 灰色RGB值 #787878
- [ ] 使用自定义搜索栏（记得关沉浸）
- [ ] 日历点击屏幕关闭

#### 笔记
[容器设置padding、margin后溢出父容器][1]  
[uni.navigateTo传递对象][2]  

##### 各种官方文档
[Web开发技术|MDN][3]  
[微信开放文档][4]  
[Vue.js][5]  
[uni-app][6]  
##### js中 = 是深复制

	可以使用扩展运算符实现浅赋值:
	
		var a = {name: "123", age: 213}
		var b = {...a}
##### pages/extUI/fav/fav 一个按钮点击示例
##### common/unit.js有时间格式化工具类
##### URL传递Object
	'&xxx=' + encodeURIComponent(JSON.stringify(this.yyy))
	this.xxx = JSON.parse(decodeURIComponent(e.xxx));
##### Json对象转自定义对象
	Object.assign(目标对象, Json对象（json.parse）);
##### 子组件使用this.$emit向调用界面传递参数
[教程链接（其实这个不太好，可以看看别的）][7]
##### watch
	
	data(){
		testVal: 0
	},
	watch:{
		testVal(val, oldVal){ // testVal改变时调用，最好使用computed替代
			console.log('new:', val, 'old:', oldVal);
		}
	}

##### v-for
	v-for="(value, key, index) in object"
	//键值 键名 索引
	v-for="(value, index) in list"
	//对象 索引
	
	v-for="item in wL(index)"
	methods: { // 如果wL不需要参数，可以放在computed中
		wL(index){
			return this.weatherList.filter(function (obj){
				return obj.index>=(index-1)*3+1 && obj.index<=index*3;
				
			})
		}
	}
	/* 
	wL(index)首先定义了一个this.weatherList的过滤器，它会把this.weatherList的成员作为参数传入一个return bool类型的匿名函数function中（如果是普通有名字的函数，只需要传入函数名，不需要加括号），返回所有返回值为true的对象，然后这些对象成为一个列表返回给v-for（？）
	*/

##### moveable-view
[教程链接][8]

[1]: https://developer.mozilla.org/zh-CN/docs/Web/CSS/box-sizing
[2]: https://blog.csdn.net/qq_16371909/article/details/91361734
[3]: https://developer.mozilla.org/zh-CN/docs/Web
[4]: https://developers.weixin.qq.com/miniprogram/dev/framework
[5]: https://cn.vuejs.org/
[6]: https://uniapp.dcloud.io/
[7]: https://blog.csdn.net/sllailcp/article/details/78595077
[8]:(https://uniapp.dcloud.io/component/movable-view?id=movable-view)