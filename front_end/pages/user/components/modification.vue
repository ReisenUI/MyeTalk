<template>
<view>
	<view>
		<view class="user-head">
			<view class="head-fixed">
					<!-- <navigator url="../user">
						<uni-icons type="back" size="50" color="#333"></uni-icons>
					</navigator> -->
				<!-- <view class="head-title">个人信息修改</view> -->
					<uni-icons type="confirm" size="50" color="#333"></uni-icons>
			</view>
			<view style="height: 100rpx;"></view>
		</view>
	<view class="tx-container">
			<image class="tx" :src="userInfo.avatar" mode="scaleToFill" @tap="ChooseImage"></image>
			<view class="tx-change">点击更换头像</view>
	</view>
		
	<view class='container'>
		
		<view class='middle-item' hover-stay-time="150">
			<!-- <navigator open-type="redirect" url="./changename" class="text-box"> -->
			<text class='text_2' style="font-weight: bold;white-space: nowrap;">昵称</text>
			<!-- <view class="right">{{userInfo.nickname}}</view> -->
			<image class="arrow" src="../../../static/arrow.png"></image>
			<input class="right" maxlength="6" style="white-space: nowrap;" :value="userInfo.nickname" @input="nicknameChange"/>
			<!-- </navigator> -->
		</view>
		<view class="view_line"></view>
			
		<view class='middle-item' hover-class="opcity" hover-stay-time="150">
			<!-- <navigator open-type="redirect" url="./changeintro" class="text-box"> -->
			<text class='text_2' style="font-weight: bold;white-space: nowrap;">简介</text>
			<!-- <view class="right"style="white-space: nowrap;">{{userInfo.introduction}}</view> -->
			<input class="right" maxlength="10" style="white-space: nowrap;" :value="userInfo.introduction" @input="introductionChange"/>
			<image class="arrow" src="../../../static/arrow.png"></image>
			<!-- </navigator> -->
		</view>
		<view class="view_line"></view>
	
		<picker :range="list1" range-key="label" mode="selector" @change="bindSexChange">
			<view class='middle-item' hover-class="opcity" hover-stay-time="150">
				<text class='text_2' style="font-weight: bold;white-space: nowrap;" >性别</text>
				<image class="arrow" src="../../../static/arrow.png"></image>
				<view class="right">{{userInfo.gender}}</view>
			</view>
		</picker>
		<view class="view_line"></view>
		
			<picker mode="date" :value="userInfo.birth"  @change="bindDateChange">
			<view class='middle-item' hover-class="opcity" hover-stay-time="150">
			<text class='text_2' style="font-weight: bold;white-space: nowrap;">生日</text>
			<image class="arrow" src="../../../static/arrow.png"></image>
				<view class="right">{{userInfo.birth}}</view>
			</view>
		</picker>
		<view class="view_line"></view>
		
		<view class='middle-item' hover-class="opcity" hover-stay-time="150">
			<text class='text_2' style="font-weight: bold;white-space: nowrap;">爱好</text>
			<image class="arrow" src="../../../static/arrow.png"></image>
			<!-- <view class="right"style="white-space: nowrap;">{{userInfo.tag}}</view> -->
			<input class="right" maxlength="10" style="white-space: nowrap;" :value="userInfo.tag" @input="tagChange"/>
		</view>
		<view class="view_line"></view>
	</view>	
	</view>
	</view>
</view>
</template>

<script>

	export default {
	        data() {
	        		return {
	        			list1: [{
								label: "男",
								value: 0,
								checked: true
							}, {
								label: "女",
								value: 1
							}],
						userInfo: {},
	        		};
	        	},
				created(){
					
				},
	        	methods: {
					getUserInfo() {
						const _this=this
						uni.request({
							url:'https://zk123.top/mye/people/info',
							data:{},
							header: {Authorization:uni.getStorageSync('token')},
							method: 'GET',
							success:(res)=>{
								// uni.showToast({
								// 	title: '资料请求成功',
								// });
								_this.userInfo = res.data.data;
								console.log(_this.userInfo)
							},
							fail: (err) => {
								uni.showToast({
									icon: 'loading',
									title:'请求失败'
								})
								setTimeout(function() {
									uni.reLaunch({
									url:'login'
								})
								}, 1200);	
							}
						})
						
						uni.getStorage({
							key:'userInfo',
							success:(res)=>{
								this.userInfo=res.data
							},
							fail:()=>{
								this.userInfo={}
							}
						})
					},
					   goBack(){
						   uni.navigateBack({
						   	delta:2
						   })
					   },
						
						saveChange(){
							var _this=this.userInfo
							uni.request({
								method:'POST',
								url:'https://zk123.top/mye/people/update',
								header: {'content-type':'application/json',
								Authorization:uni.getStorageSync('token')},
								data: {nickname:_this.nickname,introduction:_this.introduction,
								tag:_this.tag,gender:_this.gender,birth:_this.birth},
								success: (res) => {
									uni.showToast({
										title: '修改成功',
									});
									uni.$emit('saveChange',_this.nickname)
									_this = res.data;
									console.log(_this)
									setTimeout(function() {
										uni.reLaunch({
										url:'../user'
									})
									}, 1200);	
									},
								fail: (res) => {
									uni.showToast({
										icon: 'loading',
										title: '修改失败',
									});
								},
								complete: () => {}
							})
						},
					  // ChooseImage() {
						 //  uni.navigateTo({
						 //  	url:'./chooseavatar'
						 //  })
						 //  },
					  ChooseImage(){
							  var _this=this
							  // 上传图片
							  uni.chooseImage({
								count: 1,
								sourceType: ['album'],
								success(chooseImageRes) {
									const tempFilePaths = chooseImageRes.tempFilePaths;
									uni.uploadFile({
										header: {
											// 'Content-Type':'multipart/form-data',
										'Authorization':uni.getStorageSync('token')},
										url: 'https://zk123.top/mye/people/avatar',
										filePath: tempFilePaths[0],
										name: 'avatar',
										success: (uploadFileRes) => {
											console.log(uploadFileRes.data);
											_this.userInfo.avatar=JSON.parse(uploadFileRes.data)['data'];
											uni.$emit('ChooseImage',_this.userInfo.avatar)
											uni.showToast({
												title: '修改成功',
											});
											setTimeout(function() {
												uni.redirectTo({
													url:'./modification'
												})
											}, 1200);
											// uni.reLaunch({
											// 	url:'../user'
											// })
											
										}
									});
								}
							})	  
						  },
					  onNavigationBarButtonTap(e) {
					  			if(e.float=='right'){
					  			this.saveChange();
					  			}
					  		},
							
					nicknameChange(e){
						this.userInfo.nickname=e.detail.value
					},
					introductionChange(e){
						this.userInfo.introduction=e.detail.value
					},
					bindSexChange(e){
						console.log(this.list1[e.target.value].label)
						this.userInfo.gender=this.list1[e.target.value].label
					},
					bindDateChange(e){
						this.userInfo.birth=e.target.value
					},
					tagChange(e){
						this.userInfo.tag=e.detail.value
					},
	        		onLoad() {
	        			this.getUserInfo();
				},
	        }

		}
</script>

<style lang="scss">
	.user-head .head-fixed {
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 100%;
		height: 100rpx;
		background-color: #FFFFFF;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 99;
		text-align: center;
		box-sizing: border-box;
		padding: 0 30rpx;
	// border: 1px solid blue;
		.head-title {
			font-size: 38rpx;
		}
	}
	
		.tx-container{
			width: 100%;
			height: 300rpx;
			display: flex;
			align-items: center;
			text-align: center;
			flex-direction: column;
			.tx{
				// position: absolute;
				border: 1px solid #f0f0f0; 
				border-radius: 50%;
				width: 250rpx;
				height: 250rpx;
				margin:0rpx auto 0rpx;/*居中对齐*/
			}
			.tx-change{
				margin:0rpx auto 0rpx;
				color:#777777;
				font-size: 1rem;
			}
		}
		
		
		.container {
				height: 600rpx;
				display: flex;
				flex-direction: column;
				border-radius: 15rpx;
				background-color: #FFFFFF;
				margin: 80rpx 0rpx 100rpx 25rpx;
				border-radius: 10rpx;
				box-shadow: 0 0 10rpx 2rpx rgba($color: #333, $alpha: 0.1);
				box-sizing: border-box;
				// border: 1px solid red; 
				
			}
		.right{
			// border: 1px solid blue;
			// float: right;
			display: flex;
			flex-direction: row;
			font-size: 15px;
			text-align: right;
			// margin-left:300rpx;
			/* margin-right: 70px; */
			color: #515151;
		}
			.middle-item {
				// border: 1px solid black;
				height: 120rpx;
				margin-right: 80rpx;
				display: flex;
				flex-direction: row;
				align-items: center;
				justify-content: space-between;
			}
			.arrow {
				// border: 1px solid red;
					width: 32rpx;
					height: 32rpx;
					position: absolute;
					/* 要约束所在位置的子元素的位置要设置成绝对 */
					right: 30rpx;
					/* 靠右调节 */
					// margin-right: 35rpx
				}
		.view_line {
				width: 90%;
				height: 3rpx;
				background: #dcdcdc;
				/* margin: 0rxp 5% 0rxp 5%; */
				/* margin: 0 auto; */
				text-align: center;
			}
			.text_2{
				margin: 0rpx 25rpx;
				font-size: large;
			}
			
</style>
