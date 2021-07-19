<template>
	<view>
		<view class='container'>
					<view class="view_line"></view>
					<view class='middle-item' hover-stay-time="150">
						<text class='text_2' >社交账号绑定</text>
					</view>
		
					<view class="view_line"></view>
		
					<view class='middle-item' hover-class="opcity" hover-stay-time="150">
						<text class='text_2' >数据自动同步</text>
					</view>
		
					<view class="view_line"></view>
		
					<view class='middle-item' hover-class="opcity" hover-stay-time="150" v-on:click="toLock">
						<text class='text_2' >设置密码锁</text>
					</view>
		
					<view class="view_line"></view>
						
					<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="clickhandle">
						<text class='text_2'>隐私设置</text>
					</view>
					<view class="view_line"></view>
					
					<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="clickhandle">
						<text class='text_2'>日记提醒权限</text>
					</view>
					<view class="view_line"></view>
					
					<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="clickhandle">
						<text class='text_2'>社区消息提醒</text>
					</view>
					<view class="view_line"></view>
					
					<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="clickhandle">
						<text class='text_2'>用户使用说明</text>
					</view>
					<view class="view_line"></view>
					
					<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="clickhandle">
						<text class='text_2'>意见反馈</text>
					</view>
					<view class="view_line"></view>
					
					<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="connecting()">
						<text class='text_2'>联系我们</text>
					</view>
					<view class="view_line"></view>
					
			</view>
				<view class="exitBtn"@click="logout">
					<text class="btnValue">退出登录</text>
				</view>
	</view>
	
</template>

<script>
	export default{	
		data(){
			return{
				
			}
		},
		
		methods:{
			clickhandle(){
				console.log('点击我了')
			},
			toLock(){
				console.log('点击我了'),
				uni.navigateTo({
					url:"../../../components/xwa105-gesturePassword/gestureLock"
				})
			},
			connecting(){
				console.log('点击联系我们'),
				uni.showToast({
						title: '可通过以下方式联系我们',
				       	icon:'none',
				       	duration: 2000,
				})
			},
			logout(){
							// 询问是否退出登录？
							uni.showModal({
								content:'是否要退出登录',
								success:(res) => {
									if (res.confirm) {
										// this.$store.commit('logout')
										uni.reLaunch({
											url:'./login'
										})
										uni.showToast({
											title:"退出登录成功",
											icon:'none'
										})
									}
								}
							})
						}
		}
	}
</script>

<style>
	.container {
			/* position: relative; */
			top:30rpx;
			width: 96%;
			height: 1100rpx;
			display: flex;
			flex-direction: column;
			border-radius: 15rpx;
			background-color: #FFFFFF;
			margin: 0rpx 2% 100rpx 2%;
		}
	
		.middle-item {
			height: 100%;
			display: flex;
			flex-direction: row;
			align-items: center;
		}
	.view_line {
			width: 90%;
			height: 10rpx;
			background: #dcdcdc;
			/* margin: 0rxp 5% 0rxp 5%; */
			margin: 0 auto;
			text-align: center;
		}
		.text_2{
			position: relative;
			left:35rpx;
			font-size: large;
		}
		.exitBtn{
			width: 100%;
			height: 100rpx;
			background: #16b1b3;
			border-radius: 50upx;
			margin-top: -80rpx;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		.btnValue{
			color: white;
		}
</style>
