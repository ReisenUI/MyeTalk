<template>
	<view class="navbar">
		<view class="view1"  @click="gotopaint">
			<image src="../../static/png/fanhui.png"class="image1"></image>
		</view>
		<!-- <view class="nav-left">
			<image v-if="showBack" @click="navbarAction" class="menu-img" src="../../static/png/navbar_menu.png" mode="aspectFill"></image>
		</view> -->
		
		<view class="nav-middle">
			<text class="title">{{title}}</text>
		</view>
		<!-- <view class="nav-right"></view> -->
		<view class="view2" @tap="share">
			<text style="font-size: 34rpx;">发布</text>
		</view>
		
	</view>
</template>

<script>

	export default  {
		props: {
			showBack: {
				type: Boolean,
				default () {
					return true;
				}
			},
			title: {
				type: String,
				default () {
					return '极简画板';
				}
			}
		},
		data: function(){
			return {
				imagepath: null,
				// baseUrl: 'https://zk123.top/mye',
				baseUrl: 'https://zk123.top/mye',
			}
		},
		methods: {
			// baseurlToBlob(dataUrl){
			// 	let arr = dataUrl.split(','),
			// 		_arr = arr[1].substring(0, arr[1].length-2),
			// 		mime = arr[0].match(/:(.*?);/)[1],
			// 		bstr = atob(arr[1]),
			// 		n = bstr.length,
			// 		u8arr = new Uint8Array(n);
			// 	while( n-- ){
			// 		u8arr[n] = bstr.charCodeAt(n);
			// 	}
			// 	return new Blob([u8arr], { type: mime });
			// },
			// share(){
			// 	var that=this;
			// 	var path;
			// 	uni.canvasToTempFilePath({
			// 		fileType: 'jpg',
			// 		canvasId: 'drawCanvas',
			// 		success: function(res) {
			// 			// 在H5平台下，tempFilePath 为 base64
						
			// 			let blobObject = that.baseurlToBlob(res.tempFilePath);
			// 			let blobPath = URL.createObjectURL(blobObject);
			// 			let imgs = [];
			// 			let obj = {};
			// 			obj.name = 'pic';
			// 			obj.uri = blobPath;
			// 			imgs.push(obj);
			// 			console.log(res.tempFilePath, blobPath);
			// 			console.log(blobObject, obj);
			// 			uni.uploadFile({
			// 				url: that.baseUrl + '/c/post',
			// 				method: 'POST',
			// 				header:{
			// 					Authorization:uni.getStorageSync('token'),
			// 				},
			// 				files: imgs,
			// 				formData: {
			// 					'tag': 'painting',
			// 					'expireTime': '0',
			// 					'content': '',
			// 					'title': ''
			// 				},
			// 				success: (res)=>{
			// 					console.log("123s", res)
			// 					uni.showToast({
			// 						icon: 'loading',
			// 						title:'发布成功'
			// 					})
			// 				},
			// 				fail: (err) => {
			// 					console.log(err)
			// 					return uni.showToast({
			// 						icon: 'loading',
			// 						title:'请求失败'
			// 					})
			// 				}
							
			// 			})
			// 		}
			// 	})
			// },
			share(){
				this.$emit('share');
			},
			navbarAction () {
				this.$emit('navbarBtnTap');
			},
			gotopaint(){
				uni.navigateBack({
					//url:"/pages/relief/paint"
					delta:1
				})
				
			}
		}
	}
</script>

<style lang="scss">
	.view1{
		
		display: flex;
		align-items: center;
	    margin-left: 5px;
	}
	.view2{
		width: 80px;
		
		font-size: 20px;
	}
	.image1{
		height: 70rpx;
		width: 70rpx;
		
	}
	.navbar {
		height: 70px;
		background-color: #f3f3e8;
		display: flex;
		// border-bottom: 1upx $uni-border-color solid;
		align-items: center;
		
		
		
		.nav-middle {
			width: 80%;
			display: flex;
			justify-content: center;
			align-items: center;
			font-size: 25px;
			
		}
		
		
	}
	.title{
		font-size: 40rpx;
	}
</style>
