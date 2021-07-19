<template>
	<view>
		<view class="head">
			<image src="../../static/img-community/lingxian-2.jpg" mode="aspectFill"></image>
			<view class="ceng"></view>
			<view class="personal-box">
				<view class="head-photo">
					<image :src="homepageList.avatar" mode="aspectFill"></image>
				</view>
				<view class="personal">
					<view class="personal-name">{{homepageList.nickname}}</view>
					<view class="personal-info">{{homepageList.introduction}}</view>
				</view>
				<view class="tag">
					<view v-for="item in tagList">
						<view class="tag-one">{{item}}</view>
					</view>
				</view>
			</view>
		</view>

		<view class="tabs">
			<view class="tab-bar">
				故事
			</view>
		</view>

		<scroll-view class="list-scroll" scroll-y>
			<view class="content" v-if="totalSize !== 0">
				<view class="listcard" v-for="(item, index1) in articleList" :key="index1"
					@click="openPassageLingxian(item, index1)">
					<view class="listcard-head">
						<view class="head-Photo">
							<image :src="item.avatar" mode="aspectFit"></image>
						</view>
						<view class="head-name">{{item.nickname}}</view>
						<view class="head-more" @click.stop="openMenu(index1,item)">
							<uni-icons type="more-filled" size="40" color="#888"></uni-icons>
						</view>
					</view>

					<view class="myimgDV">
						<view v-if="imgList.length>0" class='receiveimage'>
							<block v-for="(item, ind) in imgList[index1]" :key="ind">
								<image class="imgitem"
									:style="{width: imgClass[index1].width, height: imgClass[index1].height}"
									:class="imgClass[index1].class" :src="item" :mode="imgClass[index1].mode"
									@click="previewpic(item,imgList[index1])"></image>
							</block>
						</view>
					</view>

					<view class="listcard-content">
						<view class="listcard-content_title">
							<view class="content_title">
								{{item.article.title}}
							</view>
							<view class="content_tag">
								{{item.article.tag}}
							</view>
						</view>
						<view class="listcard-content_describe">
							<text>{{item.article.content}}</text>
						</view>
					</view>

					<view class="listcard-bottom">
						<view class="listcard-bottom_time" v-for="(item, ind) in timeList[index1]" :key="ind">
							{{item}}
						</view>
						<view class="listcard-bottom_dianzan"  @click.stop="likeTab(item)">
							<view class="zan">
								<image :src="item.started?star[1]:star[0]"></image>
							</view>
							<view>{{item.article.star}}</view>
						</view>
						<view class="listcard-bottom_pinglun">
							<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
							<view>{{item.article.comment}}</view>
						</view>
					</view>
				</view>
				<uni-load-more iconType="snow" :status="status"></uni-load-more>
			</view>
			<view class="content-null" v-else>
				还没发表过帖子哦～
			</view>
		</scroll-view>

	</view>
</template>

<script>
	export default {
		props: {
			item: {
				type: Object,
				default () {
					return {}
				}
			}
		},
		data() {
			return {
				tabList: [{
					name: '故事'
				}],
				sticky: Boolean,
				formData: {},
				baseUrl: 'https://zk123.top/mye',
				homepageList: [],
				tagList: [],
				imgList: [],
				imgClass: [],
				timeList: [],
				articleList: [],
				uploadList: [],
				status: 'loading',
				totalSize: 0,
				pageNumber: 0,
				pageSize: 10,
				star: false,
				star: ["../../static/relief/star.png", "../../static/relief/star2.png"],
				userInfo: {},
			}
		},
		onLoad: function(option) {
			this.formData = JSON.parse(option.params);
			this.userId = this.formData.userId;
			var userId = this.formData.userId;
			var nickname = this.formData.nickname;
			// var nickname = "aaa";
			this.getHomepage(userId);
			this.getHomepageArticle(userId);
			this.getUserInfo();
			this.setTitle(nickname);
		},
		onShow() {
			const _this = this;
			uni.$on('likeTab',(res)=>{
				let currentTarget = _this.articleList;
				_this.articleList[res.index].article.star = res.star;
				uni.request({
					url: 'https://zk123.top/mye/c/judgeStar',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						id: currentTarget[res.index].article.article_id,
					},
					success: (e) => {
						currentTarget[res.index].started = e.data.data
					},
				})
			});
		},
		onReachBottom: function(){
			this.getHomepageArticle(this.userId);
		},
		methods: {
			getHomepage(userId) {
				var _this = this;
				uni.request({
					url: this.baseUrl + '/c/homepage', //接口地址
					method: 'GET',
					data: {
						userId: userId
					},
					success: (res) => {
						console.log(res.data);
						_this.homepageList = res.data.data;
						_this.tagList = _this.homepageList.tag
					}
				});
			},
			openPassageLingxian(item, index) {
				var articleId = item.article.article_id;
				uni.navigateTo({
					url: "../../pages/community-passage/passage-lingxian/passage-lingxian" +
						"?articleId=" +
						articleId + "&index=" + index
				})
			},
			openMenu(index1, item) {
				var _this = this;
				// 帖子的userId  数据的类型是string 需要先转换一下数据类型
				var article_userId = Number(item.article.article_id.slice(0, 5));
				var usrId = _this.userInfo.id;
				// 看是否为自己发的帖子
				if (_this.userInfo.id === article_userId) {
					uni.showActionSheet({
						itemList: ['删除帖子', '复制内容'],
						success: function(res) {
							var index = res.tapIndex;
							if (index === 0) {
								_this.deleteArticle(index1, item);
							} else if (index === 1) {
								console.log('复制内容');
								uni.showToast({
									title: '内容已复制'
								})
							}
						},
						fail: function(res) {
							console.log(res.errMsg);
						}
					});
				} else {
					uni.showActionSheet({
						itemList: ['复制内容'],
						success: function(res) {
							console.log(res.tapIndex);
							var index = res.tapIndex;
							if (index === 0) {
								console.log('复制内容');
								uni.showToast({
									title: '内容已复制'
								})
							}
						},
						fail: function(res) {
							console.log(res.errMsg);
						}
					});
				}
			},
			deleteArticle(index, item) {
				var _this = this;
				uni.showModal({
					title: '友情提示',
					content: '确定要删除这条帖子吗',
					success: function(res) {
						if (res.confirm) {
							uni.request({
								url: 'https://zk123.top/mye/c/article/delete',
								method: 'POST',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									articleId: item.article.article_id,
								},
								success: (res) => {
									console.log(index);
									_this.articleList.splice(index, 1);
									_this.timeList.splice(index, 1);
									_this.imgClass.splice(index, 1);
									_this.imgList.splice(index, 1);
									uni.showToast({
										title: '删除成功'
									})
								},
								fail: (err) => {
									console.log(err)
									return uni.showToast({
										title: '请求失败'
									})
								}
							})
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			getUserInfo() {
				const _this = this
				uni.request({
					url: 'https://zk123.top/mye/people/info',
					data: {},
					header: {
						Authorization: uni.getStorageSync('token')
					},
					method: 'GET',
					success: (res) => {
						_this.userInfo = res.data.data;
						console.log('userInfo', _this.userInfo)
					}
				})
			},
			getheight(imgCount) {
				if (imgCount === 0) {
					return {
						width: '0%',
						height: '0%',
						class: 'nopic',
						mode: 'aspectFill',
					};
				} else if (imgCount === 1) {
					return {
						width: '100%',
						height: '500rpx',
						class: 'onepic',
						mode: 'aspectFill'
					};
				} else if (imgCount === 2 || imgCount === 4) {
					return {
						width: '49.2%',
						height: '330rpx',
						class: 'doublepic',
						mode: 'aspectFill'
					};
				} else {
					return {
						width: '32.5%',
						height: '220rpx',
						class: 'triplepic',
						mode: 'aspectFill'
					};
				}
			},
			previewpic(cind, clist) {
				uni.previewImage({
					urls: clist,
					current: cind,
					indicator: 'default'
				});
			},
			likeTab(item) {
				console.log(item.article.article_id);
				console.log(item.started);
				const _this = this;
				var is_star = item.started;
				uni.showLoading({
					title:"加载中..."
				})
				uni.request({
					url: 'https://zk123.top/mye/c/star',
					method: 'POST',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						id: item.article.article_id,
						flag: '0'
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res);
						if (is_star == false) {
							item.started = true;
							console.log('now:' + item.started);
							item.article.star = item.article.star + 1;
							uni.showToast({
								title: '点赞成功'
							})
						} else {
							item.started = false;
							console.log('now:' + item.started);
							item.article.star = item.article.star - 1;
							uni.showToast({
								title: '取消点赞'
							})
						}
					},
					fail: (err) => {
						uni.hideLoading();
						console.log(err)
						return uni.showToast({
							title: '请求失败'
						})
					}
				})
			},
			getHomepageArticle(userId) {
				var _this = this;
				if(_this.status === 'noMore'){
					return;
				}
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/homepage/article', //接口地址
					method: 'GET',
					data: {
						userId: userId,
						limit: _this.pageSize,
						offset: ++_this.pageNumber,
					},
					success: (res) => {
						console.log(res.data);
						_this.totalSize = res.data.data.totalSize;
						_this.uploadList = res.data.data.articleList;
						if(Math.ceil(res.data.data.totalSize / this.pageSize) < this.pageNumber){
							_this.status = 'noMore';
						} else {
							(async ()=>{
								let index = _this.articleList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.pic_url === null){
											_this.imgList.push([]);
											_this.imgClass.push(_this.getheight(0));
										} else {
											_this.imgList.push(_this.uploadList[i].article.pic_url.split(","));
											_this.imgClass.push(this.getheight(_this.imgList[index].length));
										}
										index++;
										_this.timeList.push(_this.uploadList[i].article.article_id.slice(11, 16));
										_this.articleList.push(_this.uploadList[i]);
									}, ()=>{
										console.log( "err" );
									})
								}
								console.log(9998);
								_this.status = 'more';
							})();
						}
					}
				});
			},
			async judgeStars(articleId, index){
				const _this = this;
				return new Promise((resolve, reject)=>{
					uni.request({
						url: 'https://zk123.top/mye/c/judgeStar',
						header: {
							Authorization: uni.getStorageSync('token'),
							'content-type': 'application/x-www-form-urlencoded'
						}, 
						data: {
							id: articleId
						}, 
						success: (e) => {
							_this.uploadList[index].started = e.data.data;
							resolve();
						}, 
						fail: ()=>{
							reject();
						}
					})
				})
			},
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #FFFFFF;
	}

	.head {
		width: 750rpx;
		height: 600rpx;
		display: flex;
		flex-direction: column;
		position: relative;

		image {
			width: 100%;
			height: 100%;
		}

		.ceng {
			width: 750rpx;
			height: 600rpx;
			position: absolute;
			top: 0;
			background: -webkit-linear-gradient(transparent, transparent 40%, rgba(255, 255, 255, 0.9));
		}

		.personal-box {
			z-index: 9;
			background: #FFFFFF;
			width: 660rpx;
			position: absolute;
			bottom: 20rpx;
			left: 50%;
			margin-left: -330rpx;
			border-radius: 10px;
			text-align: center;
			align-items: center;
			justify-content: center;

			.head-photo {
				position: absolute;
				top: -40rpx;
				left: 50%;
				margin-left: -40rpx;
				width: 80rpx;
				height: 80rpx;

				image {
					border-radius: 50%;
				}
			}

			.personal {
				margin: 50rpx 30rpx 20rpx 30rpx;
				box-sizing: border-box;
				border-bottom: 1px solid #f2f2f2;
				padding: 10rpx 0;
				line-height: 1.5;

				.personal-name {
					font-size: 34rpx;
					color: #555;
				}

				.personal-info {
					font-size: 28rpx;
					color: #888;
					padding-bottom: 10rpx;
				}
			}

			.tag {
				display: flex;
				align-items: center;
				justify-content: space-around;
				margin: 20rpx 20rpx;
				box-sizing: border-box;
				font-size: 26rpx;

				.tag-one {
					margin: 0 20rpx;
					padding: 10rpx 20rpx;
					background: #c2e3f8;
					border-radius: 30rpx;
					color: #22a3d0;
				}
			}
		}
	}

	.tabs {
		display: flex;
		flex-direction: column;
		align-items: center;
		background-color: #ffffff;
		justify-content: center;
		box-sizing: border-box;

		.tab-bar {
			display: flex;
			width: 68rpx;
			margin: 16rpx 20rpx;
			justify-content: center;
			align-items: center;
			font-size: 30rpx;
			padding-bottom: 6rpx;
			color: #333;
			border-bottom: 2px solid #22a3d0;
		}

	}

	.list-scroll {
		height: 100%;
	}

	.listcard {
		display: flex;
		flex-direction: column;
		padding: 20rpx;
		margin: 16rpx 12rpx;
		border-radius: 8rpx;
		box-shadow: 0 0 10rpx 2rpx rgba($color: #000000, $alpha: 0.1);
		box-sizing: border-box;
		background: #FFFFFF;

		.listcard-head {
			display: flex;
			float: left;
			width: 100%;
			height: 75rpx;
			justify-content: space-between;
			align-items: center;
			position: relative;

			.head-Photo {
				width: 60rpx;
				height: 60rpx;
				overflow: hidden;

				image {
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}

			.head-name {
				font-size: 30rpx;
				color: #555555;
				position: absolute;
				left: 80rpx;
			}
		}

		.myimgDV {

			.receiveimage {
				margin-top: 8rpx;
				display: flex;
				flex-wrap: wrap;

				.onepic {
					margin-bottom: 8rpx;

					&:nth-child(3n) {
						margin-right: 0;
					}
				}

				.doublepic {
					margin-right: 8rpx;
					margin-bottom: 8rpx;

					&:nth-child(2n) {
						margin-right: 0;
					}
				}

				.triplepic {
					margin-right: 8rpx;
					margin-bottom: 8rpx;

					&:nth-child(3n) {
						margin-right: 0;
					}
				}
			}
		}

		.listcard-content {
			display: flex;
			flex-direction: column;
			width: 100%;

			.listcard-content_title {
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-weight: 400;
				margin: 10rpx 0;

				.content_title {
					font-size: 32rpx;
					color: #000;
					line-height: 1.8;
				}

				.content_tag {
					padding: 8rpx 24rpx;
					border-radius: 32rpx;
					background-color: #ccd8f2;
					box-shadow: 0 0 10rpx 2rpx rgba($color: #1294ca, $alpha: 0.1);
					color: #666;
					font-size: 28rpx;
				}
			}

			.listcard-content_describe {
				font-size: 28rpx;
				color: #444;
				line-height: 1.5;

				text {
					overflow: hidden;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-line-clamp: 3;
					-webkit-box-orient: vertical;
				}
			}
		}

		.listcard-bottom {
			display: flex;
			float: left;
			width: 100%;
			height: 75rpx;
			// justify-content: space-around;
			align-items: center;
			position: relative;
			font-size: 26rpx;
			color: #888888;
			position: relative;

			.listcard-bottom_dianzan {
				display: flex;
				position: absolute;
				right: 100rpx;

				.zan {
					margin-top: 2rpx;
					padding-right: 10rpx;
					width: 40rpx;
					height: 40rpx;
					image{
						width: 100%;
						height: 100%;
					}
				}
			}

			.listcard-bottom_pinglun {
				display: flex;
				position: absolute;
				right: 20rpx;

				.ping {
					margin-top: 5rpx;
					padding-right: 10rpx;
				}
			}
		}
	}


	.content-null {
		display: flex;
		height: 300rpx;
		line-height: 300rpx;
		justify-content: center;
		color: #333;
	}
</style>
