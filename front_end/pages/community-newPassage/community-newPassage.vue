<template>
	<view class="page">
		<view class="newPassage">
			<view class="secion" @click="actionSheetTap">
				<view class="section_title">选择分区</view>
				<view class="section_info">{{region}}></view>
			</view>

			<view class="content">
				<textarea class="content_title" v-model="title" placeholder="起个标题叭..." />
				<textarea class="content_info" v-model="content" maxlength='-1' placeholder="有趣的故事,认真的提问,真诚的分享,讲究的图文更容易吸引人哦～" />
			</view>
			
			<view class="addImage_title">
				<view class="name">添加图片：</view>
				<view class="info">{{imageList.length}}/9</view>
			</view>
			
			<view class="addImage-box">
				<view class="addImage-item" v-for="(item,index) in imageList" :key="index">
					<view class="close-icon" @click="del(index)">
						<uni-icons type="closeempty" size="34" color="#fff"></uni-icons>
					</view>
					<view class="image-box">
						<image :src="item.url" mode="aspectFill"></image>
					</view>
				</view>
				
				<view v-if="imageList.length < 9" class="addImage-item" @click="chooseImage">
					<view class="image-box">
						<uni-icons type="plusempty" size="100" color="#999"></uni-icons>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: '',
				content: '',
				buttonRect: {},
				imageList: [],
				imgs: [],
				baseUrl: 'https://zk123.top/mye',
				currentRegion: 0,
				region: '选择合适的分区才会有更好的沟通哦',
			}
		},
		/* onNavigationBarButtonTap(e) {
		    // uni.navigateTo({
		    //     url: '/pages/community/community'
		    // });
			// navigateBack() {
				uni.navigateBack();
			// }
			// console.log('1')
		}, */
		// #ifdef H5
		onReady() {
			this.getNodeInfo()
			window.addEventListener('resize', this.getNodeInfo)
		},
		beforeDestroy() {
			window.removeEventListener('resize', this.getNodeInfo)
		},
		// #endif
		methods: {
			actionSheetTap() {
				const that = this
				uni.showActionSheet({
					title: '选择分区',
					itemList: ['解忧杂货铺', '阿基米德专区'],
					popover: {
						top: that.buttonRect.top * 2 + that.buttonRect.height,
						left: that.buttonRect.left + that.buttonRect.width / 2
					},
					success: (e) => {
						this.currentRegion = e.tapIndex;
						if(this.currentRegion === 1){
							this.region = '阿基米德专区'
						} else {
							this.region = '解忧杂货铺'
						}
					}
				})
			},
			// #ifdef H5
			getNodeInfo() {
				let i = 1
				uni.createSelectorQuery().select('.secion').boundingClientRect().exec((ret) => {
					const rect = ret[0]
					if (rect) {
						this.buttonRect = rect
					}
				});
			},
			// #endif
			
			del(index) {
				this.imageList.splice(index, 1)
			},
			onNavigationBarButtonTap(e) {
				if(e.float=='right'){
					let that = this;
					let reg = /\#(.+?)\#/;
					let result;
					that.imgs = [];
					const images = that.imageList;
					const imglen = images.length;
					let taglist = [];
					while((result = reg.exec(that.content)) != null){
						let tagIndex = result.index;
						let tagEnd = tagIndex + result[1].length + 2;
						that.content = that.content.substr(0, tagIndex) + that.content.substr(tagEnd);
						let curTag = result[1].replace(',', '');
						taglist.push(curTag);
					}
					uni.showToast({
						icon: 'loading',
						title: '正在发表，请稍等'
					})
					if( taglist.length === 0){
						taglist.push('社区生活');
					}
					this.loadImgs(images).then((returnData)=>{
						uni.request({
							url: that.baseUrl + '/c/post',
							method: 'POST',
							header: {
								Authorization: uni.getStorageSync('token'),
								'content-type': 'application/x-www-form-urlencoded',
							},
							data: {
								title: that.title,
								imgPathList: returnData,
								content: that.content,
								tag: taglist,
								expireTime: that.currentRegion
							},
							success: (res) =>{
								uni.hideToast();
								console.log(res);
								uni.reLaunch({
									url: '../community/community'
								})
							},
							fail: (err)=>{
								uni.hideToast();
								console.log(err);
								uni.showToast({
									icon: 'none',
									title: '发表失败'
								})
							}
						});
					}, (returnCode)=>{
						if(returnCode === 1008){
							uni.showToast({
								icon: 'none',
								title: '呀，有个图片的格式不支持呢'
							})
						} else {
							uni.showToast({
								icon: 'none',
								title: '图片上传时候好像出了些问题...'
							})
						}
					})
				}
			},
			chooseImage() {
				const count = 9 - this.imageList.length
				uni.chooseImage({
					count:count,
					sourceType: ['album'],
					success:(res) => {
						const tempfilepaths = res.tempFilePaths
						tempfilepaths.forEach((item, index)=>{
							// 处理图片多选的状况
							if(index < count) {
								this.imageList.push({
									url: item
								})
							}
						})
					}
				})
			},
			async loadImgs(imgLists){
				const imglen = imgLists.length;
				const that = this;
				let lists = [];
				let uploadCount = 0;
				return new Promise((resolve, reject)=>{
					if( imglen === 0 ){
						console.log(111);
						resolve(lists);
					}
					for( let i = 0; i < imglen; ++i){
						uni.uploadFile({
							header: {
								Authorization: uni.getStorageSync('token')
							},
							method: 'POST',
							url: this.baseUrl + '/c/upload',
							name: 'file',
							filePath: imgLists[i].url,
							success: (res)=>{
								const returnData = JSON.parse(res.data);
								if( returnData.code !== 200){
									reject(returnData.code);
								} else {
									lists.push(returnData.data);
									uploadCount ++;
									if(uploadCount === imglen){
										resolve(lists);
									}
								}
							},
							fail: (err)=>{
								reject(-1);
							},
						})
					}
				})
			},
		}
	}
</script>

<style lang="scss">
	.newPassage {
		background: #FFFFFF;
	}

	.secion {
		background: #f5f5f5;
		display: flex;
		height: 100rpx;
		color: #555;
		align-items: center;
		justify-content: space-between;
		box-sizing: border-box;
		padding: 20rpx 30rpx;
		.section_title {
			font-size: 32rpx;
		}
		.section_info {
			font-size: 28rpx;
		}
	}
	
	.content {
		box-sizing: border-box;
		padding: 20rpx 40rpx;
		display: flex;
		flex-direction: column;
		.content_title {
			box-sizing: border-box;
			width: 100%;
			height: 100rpx;
			line-height: 100rpx;
			font-size: 32rpx;
			border-bottom: 1px solid #ececec;
		}
		.content_info {
			padding: 20rpx 0;
			width: 100%;
			font-size: 30rpx;
			line-height: 1.8;
		}
	}

	.addImage_title {
		box-sizing: border-box;
		padding: 20rpx 36rpx;
		font-size: 30rpx;
		color: #555;
		display: flex;
		justify-content: space-between;
	}
	.addImage-box {
		display: flex;
		flex-wrap: wrap;
		padding: 20rpx;
		.addImage-item {
			position: relative;
			width: 33.33%;
			height: 0;
			padding-top: 33.33%;
			box-sizing: border-box;
			.close-icon {
				display: flex;
				justify-content: center;
				align-items: center;
				position: absolute;
				right: 10rpx;
				top: 10rpx;
				z-index: 2;
				width: 22px;
				height: 22px;
				border-radius: 10rpx;
				background-color: #9f9f9f;
			}
			.image-box {
				display: flex;
				justify-content: center;
				align-items: center;
				position: absolute;
				top: 10rpx; 
				bottom: 10rpx;
				left: 10rpx;
				right: 10rpx;
				border: 1px solid #eee;
				border-radius: 10rpx;
				overflow: hidden;
				
				image {
					width: 100%;
					height: 100%;
				}
			}
		}
	}
	.bottom-card {
		height: 100rpx;
		width: 100%;
		box-sizing: border-box;
		line-height: 80rpx;
		padding: 0 20rpx;
		.cu-btn{
			justify-content: center;
			text-align: center;
			background-color: #F5F5F5;
			border-radius: 20rpx;
			.padding-tb-sm {
				color: #555;
			}
		}
	}
</style>
