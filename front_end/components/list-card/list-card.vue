<template>
	<view>
		<!-- <mescroll-body ref="mescrollItem" @init="mescrollInit" @down="downCallback" @up="upCallback"> -->
		<!-- 基础卡片 -->
		<view class="listcard" @click.stop="openPassageLingxian" v-for="(item, index1) in articleList"
			:key="item.article_id">

			<view class="listcard-head">
				<view class="head-Photo" @click.stop="openPersonal">
					<image :src="item.avatar" mode="aspectFit"></image>
				</view>
				<view class="head-name" @click.stop="openPersonal">{{item.nickname}}</view>
				<view class="head-more">
					<uni-icons type="more-filled" size="40" color="#888"></uni-icons>
				</view>
			</view>

			<view class="myimgDV">
				<view v-if="imgList.length>0" class='receiveimage flex justify'>
					<block v-for="(item, ind) in imgList[index1]" :key="ind">
						<image class="imgitem" :style="{width: imgClass[index1].width, height: imgClass[index1].height}"
							:class="imgClass[index1].class" :src="item" :mode="imgClass[index1].mode"
							@click="previewpic(item,imgList[index1])"></image>
					</block>
				</view>
			</view>

			<view class="listcard-content">
				<view class="listcard-content_title">
					{{item.article.title}}
				</view>
				<view class="listcard-content_describe">
					<text>{{item.article.content}}</text>
				</view>
			</view>

			<view class="listcard-bottom">
				<view class="listcard-bottom_time" v-for="(item, ind) in timeList[index1]" :key="ind">{{item}}
				</view>
				<view class="listcard-bottom_dianzan" @click.stop="likeTab">
					<uni-icons class="zan" :type="star?'hand-thumbsup-filled':'hand-thumbsup'" size="34" color="#888">
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
		<!-- </mescroll-body> -->
	</view>
</template>

<script>
	// import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	/* import {
		getArticle
	} from '../../APImodels/recommend.js' */
	export default {
		name: "list-card",
		// mixins: [MescrollMixin], // 使用mixin
		props: {
			item: {
				type: Object,
				default () {
					return {}
				}
			},
			// 父组件向子组件传递数据
			// receiveArticleList: {
			// 	// 数据类型是数组
			// 	type: Array,
			// 	// 返回一个空数组
			// 	default () {
			// 		return []
			// 	}
			// },
		},
		data() {
			return {
				baseUrl: 'https://zk123.top/mye',
				articleList: [],
				uploadList: [],
				imgList: [],
				imgClass: [],
				timeList: [],
				star: false,
				status: 'more',
				userId: 1,
				tag: '',
				pageSize: 10,
				pageNumber: 0,
			};
		},
		created() {
			console.log('created');
			this.getArticleList();
		},
		mounted() {
			let _this = this;
			uni.$once('onReachBottom', function(data) {
				console.log('调用了');
				// _this.pageNumber++;
				_this.getArticleList();
				/* console.log('调用了');
				
				uni.request({
					url: _this.baseUrl + '/c/article', //接口地址
					method: 'GET',
					data: {
						userId: _this.userId,
						tag: _this.tag,
						limit: _this.pageSize,
						offset: _this.pageNumber
					},
					success: (res)=>{
						pageNumber++;
						console.log('当前offeset：', _this.pageNumber);
						for (var i = _this.articleList.length; i < res.data.data.totalSize; i++) {
							_this.articleList = _this.articleList.concat(res.data.data.articleList[
							i]); //将数据拼接在一起
						}
						_this.status = 'more';
					}
				}) */
			})
		},
		methods: {
			// 图片预览
			previewpic(cind, clist) {
				// console.log(cind, clist);
				uni.previewImage({
					urls: clist,
					current: cind,
					indicator: 'default'
				});
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
			likeTab() {
				console.log('点赞成功');
				this.star = !this.star;
			},
			getArticleList(pageNumber) {
				var _this = this;
				if (_this.status === 'noMore') {
					return
				}
				_this.status = 'loading';
				_this.pageNumber++;
				console.log('当前offeset：', this.pageNumber);
				uni.request({
					url: this.baseUrl + '/c/article', //接口地址
					method: 'GET',
					data: {
						userId: this.userId,
						tag: this.tag,
						limit: this.pageSize,
						offset: this.pageNumber
					},
					success: (res) => {
						/* console.log(res.data)
						let articleList = [];
						let obj = res.data.data.articleList;
						for (var i = 0; i < 10; i++) {
							articleList.push(res.data.data[i]);
								for (let i = 0; i < obj.length; i++) {
									_this.imgList.push(obj[i].article.pic_url.split(","));
									_this.timeList.push(obj[i].article.article_id.slice(11, 16));
									_this.imgClass.push(this.getheight(_this.imgList[i].length));
								};
						}
						_this.articleList = articleList;
						if (res.data.data.totalSize === res.data.data.articleList.length) {
							uni.showToast({
								title: '已是最新',
								duration: 2000
							});
						} */
						console.log(res.data);
						_this.uploadList = res.data.data.articleList;
						if (Math.ceil(res.data.data.totalSize / this.pageSize) < this.pageNumber) {
							_this.status = 'noMore';
						} else {
							// console.log(_this.articleList);
/* 							let obj = res.data.data.articleList;
							for (let i = 0; i < obj.length; i++) {
								_this.imgList.push(obj[i].article.pic_url.split(","));
								_this.timeList.push(obj[i].article.article_id.slice(11, 16));
								_this.imgClass.push(this.getheight(_this.imgList[i].length));
							}; */
							setTimeout(function() {
								for (var i = 0; i < _this.uploadList.length; i++) {
									// _this.imgList.push(_this._uploadList[i].article.pic_url.split(","));
									// _this.timeList.push(_this.uploadList[i].article.article_id.slice(11, 16));
									// _this.imgClass.push(_this.getheight(_this.imgList[i].length));
									_this.articleList.push(_this.uploadList[i]);
								}
								console.log(_this.articleList.length);
								_this.status = 'more'
								console.log(_this.articleList);
							}, 1000);
							// _this.articleList = _this.articleList.concat(res.data.data.articleList);

							// setTimeout(function() {
							// 	let oldList = obj || []
							// 	oldList.push(...data)
							// 	_this.status = 'loading'
							// }, 1000);
						}
					}
				});
			},


			openPersonal() {
				uni.navigateTo({
					url: "../../pages/community-personalCenter/community-personalCenter"
				})
			},
			openPassageLingxian() {
				uni.navigateTo({
					url: "../../pages/community-passage/passage-lingxian/passage-lingxian"
				})
			}
		}

	}
</script>

<style lang="scss">
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
			.flex {
				display: flex;
			}

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
				font-size: 32rpx;
				font-weight: 400;
				color: #000;
				font-weight: 400;
				line-height: 1.8;
				padding-bottom: 10rpx;
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

</style>
