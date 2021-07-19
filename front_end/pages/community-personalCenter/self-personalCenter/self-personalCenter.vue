<template>
	<view>
		<view class="head">
			<image src="../../../static/img-community/lingxian-2.jpg" mode="aspectFill"></image>
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
			<scroll-view scroll-x class="tab-bar">
				<view style="flex-direction: column;">
					<view class="title-scroll-box" style="flex-direction: row;">
						<view class="title-scroll-item" :class="{ active:activeIndex === index}"
							@click="clickTitle(item, index)" v-for="(item, index) in tabList" :key="index"
							@change="change" :titleIndex="titleIndex" :activeIndex="activeIndex">
							{{item.name}}
						</view>
					</view>
				</view>
			</scroll-view>
		</view>

		<swiper class="community-swiper" @change="change" :current="activeIndex">
			<swiper-item class="swiper-item">
				<scroll-view class="list-scroll" scroll-y>
					<view class="content" v-if="totalSize !== 0">
						<view class="listcard" v-for="(item, index1) in articleList" :key="index1"
							@click="openPassageLingxian(item)">
							<view class="listcard-head">
								<view class="head-Photo">
									<image :src="item.avatar" mode="aspectFit"></image>
								</view>
								<view class="head-name">{{item.nickname}}</view>
								<view class="head-more">
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
								<view class="listcard-bottom_dianzan" @click.stop="likeTab">
									<uni-icons class="zan" :type="star?'hand-thumbsup-filled':'hand-thumbsup'" size="34"
										color="#888">
									</uni-icons>
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
			</swiper-item>
			<swiper-item class="swiper-item">
				<scroll-view class="list-scroll" scroll-y>
					<story-comment></story-comment>
					<story-comment></story-comment>
					<story-comment></story-comment>
					<uni-load-more iconType="snow" status="noMore"></uni-load-more>
				</scroll-view>
			</swiper-item>

		</swiper>
		
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
		// 可以监听 data props 值的变化
		watch: {
			titleIndex(newValue) {
				this.activeIndex = newValue
			}
		},
		data() {
			return {
				tabList: [{
						name: '故事'
					},
					{
						name: '故事评论'
					}
				],
				sticky: Boolean,
				baseUrl: 'https://zk123.top/mye',
				activeIndex: 0,
				titleIndex: 0,
				homepageList: [],
				tagList: [],
				imgList: [],
				imgClass: [],
				timeList: [],
				articleList: [],
				status: 'loading',
				totalSize: 0,
				star: false,
			}
		},
		onLoad: function(option) {
			this.userId = option.userId;
			var userId = option.userId;
			this.getHomepage(userId);
			this.getHomepageArticle(userId);
		},
		methods: {
			change(e) {
				const {
					current
				} = e.detail
				// console.log(e);
				// console.log('当前swiper的值：',current)
				this.$emit('change', current)
				this.titleIndex = current
			},
			clickTitle(item, index) {
				// console.log(item, index);
				this.activeIndex = index;
				// this.$emit调用的组件会接收这个事件
				this.$emit('title', {
					data: item,
					index: index
				})
			},
			getHomepage(userId) {
				uni.showLoading({
					title: "个人信息加载中..."
				})
				var _this = this;
				uni.request({
					url: this.baseUrl + '/c/homepage', //接口地址
					method: 'GET',
					data: {
						userId: userId
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res.data);
						_this.homepageList = res.data.data;
						_this.tagList = _this.homepageList.tag
					}
				});
			},
			openPassageLingxian(item) {
				var articleId = item.article.article_id;
				uni.navigateTo({
					url: "../../pages/community-passage/passage-lingxian/passage-lingxian"
					+ "?articleId="
					+ articleId
				})
			},
			getheight(imgCount) {
				if (imgCount === 1) {
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
			getHomepageArticle(userId) {
				uni.showLoading({
					title: "故事正在加载中..."
				})
				var _this = this;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/homepage/article', //接口地址
					method: 'GET',
					data: {
						userId: userId,
						limit: 100,
						offset: 1
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res.data);
						_this.totalSize = res.data.data.totalSize;
						_this.articleList = res.data.data.articleList;
						let obj = res.data.data.articleList;
						for (let i = 0; i < obj.length; i++) {
							_this.imgList.push(obj[i].article.pic_url.split(","));
							_this.imgClass.push(this.getheight(_this.imgList[i].length));
							_this.timeList.push(obj[i].article.article_id.slice(11, 16));
						};
						_this.status = 'noMore';
					}
				});
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

				/* .tag-two {
					padding: 10rpx 20rpx;
					background: #e5cfff;
					border-radius: 30rpx;
					color: #b827ff;
				}

				.tag-three {
					padding: 10rpx 20rpx;
					background: #ffe5e8;
					border-radius: 30rpx;
					color: #ff9fb0;
				} */
			}
		}
	}

	.tabs {
		z-index: 990;
		position: sticky;
		top: var(--window-top);
		display: flex;
		flex-direction: column;
		overflow: hidden;
		flex-shrink: 0;
		background-color: #ffffff;
		justify-content: center;
		align-items: center;
		box-sizing: border-box;
		border-top: 1px solid #EEEEEE;
		border-bottom: 1px solid #EEEEEE;

		.tab-bar {
			width: 750rpx;
			flex-direction: row;
			flex-wrap: nowrap;
			display: flex;
			box-sizing: border-box;
			margin: 0rpx 20rpx;

			.title-scroll-box {
				display: flex;
				align-items: center;
				justify-content: space-around;
				text-align: center;
				flex-wrap: nowrap;
				box-sizing: border-box;
				padding-bottom: 10rpx;

				.title-scroll-item {
					font-size: 30rpx;
					padding: 14rpx 0;
					color: #777;

					&.active {
						border-bottom: 2px solid #22a3d0;
						color: #000;
						transition-duration: 0.2s;
						transition-property: left;
					}
				}
			}
		}

	}


	swiper {
		height: 1156rpx;
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
				.content_title{
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
