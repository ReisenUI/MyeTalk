<template>
	<view class="card" :style="{backgroundImage:`url(${indexBackgroundImage})`,backgroundSize: 'cover'}">
		<view class="content-container">
			<view class="title">心情说</view>
			<view class="title2">做自己情绪的主人</view>
			<view class="form">
				<image class="icon1" src="../../../static/account.png" style="width: 45rpx;height: 45rpx;"></image>
				<view class="inputWrapper">
					<input class="input" type="text" v-model="email" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  placeholder="请输入邮箱账号"/>
				</view>
				<!-- <view class="view_line"></view> -->
				<image class="icon1" src="../../../static/password.png"style="width: 50rpx;height: 55rpx;margin-top: 70rpx;"></image>
				<view class="inputWrapper">
					<input class="input" type="password" v-model="password" onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  placeholder="请输入密码"/>
				</view>
				<!-- <view class="view_line"></view> -->
				<view class="loginBtn"@click="bindLogin"hover-class="bg-click">
					<text class="btnValue">登录</text>
				</view>
				<view class="forgotBtn"@click="toRegister">
					<text>注册账号</text>
				</view>
				<view class="forgotBtn">
					<text style="color: #555555;">找回密码</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
		import indexBackgroundImage from "@/static/bg.jpg"
	export default{
		data() {
			return {
				animationData2: {},
				email:"aa@qq.com",
				password:"12345",
				token:"",
				indexBackgroundImage:indexBackgroundImage
			};
		},
		onUnload() {
		      // 页面关闭后清空数据
		},
		onload(){   
			this.animation2 = uni.createAnimation()  
		    // 创建动画实例
		},
		methods: {
			toRegister(){
				uni.navigateTo({
				    url: './register'
				});
			},
			bindLogin(){
				const token = uni.getStorageSync('token');
				const _this = this;
				const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
				if(_this.email === "" || _this.password === ""){
					uni.showToast({
						icon: 'none',
						title:"用户名和密码不能为空",
						duration:2000,
					});
				} else {
					if( reg.test(_this.email) === true  ){
						if((_this.email!="")&&(_this.password!="")){
							uni.request({
								url: 'https://zk123.top/mye/login?email='+_this.email+'&password='+_this.password,
								method: 'POST',
								data: {
									email : _this.email, 
									password : _this.password,
								},
								success: (res) => {
									if(res.data.code == 1002||res.data.code == 1003){
										uni.showToast({
											icon: 'none',
											title: '密码或用户名错误',
										});
										return;
									} else {
										if(res.data.code == 200){
											uni.showToast({
												icon: 'none',
												title: '登录成功',
											});
										}
										uni.setStorage({
											key: 'email',
											data: _this.email,
											success:function(){
												console.log("存储用户名到本地成功！");
											}
										})								
										uni.setStorage({
											key: 'token',
											data: res.data.data,
											success: function () {
												uni.getStorage({
												key: 'token',
													success: function (res) {
														console.log(token);
													}
												});
											}
										});
										uni.reLaunch({
											url:'../../index/index',
										})
									}
								},
								fail: () => {},
								complete: () => {}
							});
						}
					} else {
						uni.showToast({
							icon: 'none',
							title: '邮箱格式不正确哦',
							duration: 1000,
						});
					}
				}
			},
		},	
	}
</script>

<style lang="scss">
	.content-container{
		display: block;
		padding-top: 150rpx;
	}
	
	.card{
		width: 100vw;
		height: 100vh;
		background-color:#f3f3f3;


		.title{
			width: 100%;
			height: 200rpx;
			text-align: left;
			font-size: 80rpx;
			padding-left: 100rpx;
			padding-top: 100rpx;
			// margin-left: 100rpx;
			// border: 1px solid #aaa;
		}
		.title2{
			// width: 100%;
			// height: 100rpx;
			text-align: center;
			font-size: 30rpx;
			margin-left: 150rpx;
			margin-top: -80rpx;
			// border: 1px solid #aaa;
		}
	}
	.img-container{
		width: 100%;
		height: 300rpx;
		display: flex;
		align-items: center;
		.img1{
			// position: absolute;
			border: 1px solid #aaa; 
			border-radius: 50%;
			width: 300rpx;
			height: 300rpx;
			margin:50rpx auto 0rpx;/*居中对齐*/
		}
	}
	
	.form{
			padding: 0 100rpx;
			margin-top: 50rpx;
		}
		.icon1{
			position: absolute;
			left: 120rpx;
			margin-top: 25rpx;
			// width: 50rpx;
			// height: 50rpx;
			
		}
		.inputWrapper{
			width: 100%;
			height: 80rpx;
			background: transparent;
			// border-radius: 20rpx;
			border-bottom: 1px solid #303030;
			box-sizing: border-box;
			padding: 0 20rpx;
			margin-top: 50rpx;
		}
		.inputWrapper .input{
			width: 100%;
			height: 100%;
			text-align: left;
			font-size: 30rpx;
			margin-left: 80rpx;
		}
		.loginBtn{
			width: 100%;
			height: 80upx;
			background: #000000;
			border-radius: 50rpx;
			margin-top: 80rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			
		}
		.bg-click{
			top: 2upx;
			background-color: #282828;
		}
		.registerBtn{
			width: 100%;
			height: 80upx;
			background: #272727;
			border-radius: 50upx;
			margin-top: 30rpx;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		.btnValue{
			color: white;
		}
		.forgotBtn{
			text-align: center;
			color: #000000;
			font-size: 15px;
			margin-top: 20px;
		}
</style>
