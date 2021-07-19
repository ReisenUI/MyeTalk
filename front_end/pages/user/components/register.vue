<template>
	<view class="card" :style="{backgroundImage:`url(${indexBackgroundImage})`,backgroundSize: 'cover'}">
		<view class="title">心情说</view>
		<view class="title2">做自己情绪的主人</view>
	<view class="form">
				<image class="icon2" src="../../../static/account.png"style="width: 45rpx;height: 45rpx;"></image>
				<view class="inputWrapper">
					<input class="input" type="text" v-model="email" placeholder="请输入邮箱账号"/>
				</view>
				<view class="view_line"></view>
				<image class="icon2" src="../../../static/password.png"style="width: 50rpx;height: 55rpx;margin-top: 70rpx;"></image>
				<view class="inputWrapper">
					<input class="input" type="password" v-model="password" placeholder="请输入密码"/>
				</view>
				<view class="view_line"></view>
				<image class="icon2" src="../../../static/password.png"style="width: 50rpx;height: 55rpx;margin-top: 70rpx;"></image>
				<view class="inputWrapper">
					<input class="input" type="password" v-model="password1" placeholder="请重复输入密码"/>
				</view>
				<view class="view_line"></view>
				<view class="get" @tap="getchecknum()">
				 <text>{{!codetime?'获取验证码':codetime+'s'}}</text>
				 </view>
				<image class="icon2" src="../../../static/verificationCode.png"style="width: 50rpx;height: 55rpx;margin-top: 70rpx;"></image>
				<view class="inputWrapper">
					<input class="input" type="text" v-model="checknum" placeholder="请输入验证码"/>
				</view>
				<view class="view_line"></view>
				<view class="registerBtn"@click="btnReg">
					<text class="btnValue">注册</text>
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
				email:"",
				password:"",
				password1:"",
				checknum:"",
				codetime:0,
				indexBackgroundImage:indexBackgroundImage
			};
		},
		onUnload() {
			  this.animationData2 = {}
		      // 页面关闭后清空数据
		    },
		    onload(){
					this.animation2 = uni.createAnimation()  
		        // 创建动画实例
		    },
		methods: {
					getchecknum(){
						const _this=this
						const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
						if(_this.email === ""){
							uni.showToast({
								icon: 'none',
								title:"邮箱不能为空",
								duration:2000,
								});
						}else {
							if(reg.test(_this.email) === true){
								uni.request({
									url: 'https://zk123.top/mye/register/sendCode?email='+_this.email,
									method: 'POST',
									data: {
										email:_this.email, 
									},
									success: (res) => {
										console.log(res.data.code);
										if( res.data.code === 1014){
											uni.showToast({
												icon: "none",
												title: "该邮箱已被使用",
												duration: 2000,
											})
										} else {
											uni.showToast({
												title:"验证码发送成功",
												duration:2000,
											});
											if(this.codetime>0){
												uni.showToast({
												title: '不能重复获取',
												icon:"none"
												});
												return;
											}else{
												this.codetime = 60
												let timer = setInterval(()=>{
												this.codetime--;
												if(this.codetime<1){
													clearInterval(timer);
													this.codetime = 0
													}
												},1000)
											}
										}
									},
									fail: () => {},
									complete: () => {}
								});
							} else {
								uni.showToast({
									icon: 'none',
									title:"邮箱格式不正确哦",
									duration:2000,
									});
							}
						}
						
					},
					btnReg(){
						const _this=this
						const reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
						if(_this.email === "" || _this.password === ""||_this.checknum === ""){
							uni.showToast({
								icon: 'none',
								title:"用户名、密码和验证码均不能为空",
								duration:2000,
								});
						} else if( _this.password !== _this.password1 ){
							uni.showToast({
								icon: 'none',
								title:"两次输入密码不一致",
								duration:2000,
								});
						} else {
							if(reg.test(_this.email) === true){
								uni.request({
									url: 'https://zk123.top/mye/register?email='+_this.email+'&password='+_this.password+'&code='+_this.checknum,
									method: 'POST',
									data: {
										email:_this.email, 
										password:_this.password,
										code:_this.checknum
									},
									success: (res) => {
										console.log(res.data.code);
										if( res.data.code === 1014){
											uni.showToast({
												icon: "none",
												title: "该邮箱已被使用",
												duration: 2000,
											})
										} else {
											uni.showToast({
												title:"注册成功",
												duration:2000,
												success() {
													setTimeout(function () {
														uni.navigateTo({
															url: './login',
														});
													}, 1000);
												}
											});
										}
									},
									fail: () => {},
									complete: () => {}
								});
							} else {
								uni.showToast({
									icon: 'none',
									title:"邮箱格式不正确哦",
									duration:2000,
									});
							}
						}
					},
					running2() {
					        this.animation2 = uni.createAnimation()
					        this.animation2
							    // .translateX(150).step()
							    .translateY(20).step()
								.translateY(-20).step()
					        this.animationData2 = this.animation2.export()
					        
					        // setTimeout(()=>{
					        //   this.animation2.translateX(150).opacity(0).step({duration:0})
					        //   this.animationData2 = this.animation2.export()
					        // }, 800);
					      }
				},
				
	}
	
</script>

<style lang="scss">
	.card{
		width: 100%;
		height: 96vh;
		// background-color:#f3f3f3;
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
	.form{
		/* border: 1px solid #aaa; */
			padding: 0 100rpx;
		}
		.view_line {
				width: 90%;
				height: 5rpx;
				/* background: #dcdcdc; */
				background: #303030;
				/* margin: 0rxp 5% 0rxp 5%; */
				margin: 0 auto;
				text-align: center;
			}
		.icon2{
			position: absolute;
			left: 120rpx;
			margin-top: 25rpx;
			
		}
		.inputWrapper{
			width: 100%;
			height: 80rpx;
			background: transparent;
			border-radius: 20rpx;
			box-sizing: border-box;
			padding: 0 20rpx;
			margin-top: 50rpx;
		}
		.inputWrapper .input{
			width: 100%;
			height: 100%;
			text-align: center;
			font-size: 30rpx;
		}
		.registerBtn{
			width: 100%;
			height: 80upx;
			background: #000000;
			border-radius: 50upx;
			margin-top: 30rpx;
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 50rpx;
		}
		.btnValue{
			color: white;
		}
		.get{
		 position: absolute;
		 top: 720rpx;
		 right: 30rpx;
		 background-color: black;
		 height: 70rpx;
		 line-height: 70rpx;
		 color: white;
		 border-radius: 10rpx;
		 padding: 0 20rpx;
		}
</style>
