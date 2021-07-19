<template>
	<view class="community-passage">

		<view class="navbar">
			<view class="navbar-fixed">
				<view class="navbar-return" @click="backCommunity">
					<uni-icons type="arrowleft" size="50" color="#999"></uni-icons>
				</view>
				<view class="navbar-authorPhoto">
					<image :src="authorDetail.avatar" mode="aspectFit"></image>
				</view>
				<view class="navbar-authorName">
					{{authorDetail.nickname}}
				</view>
			</view>
			<view style="height: 54px;"></view>
		</view>

		<view class="image">
			<swiper indicator-dots="true" circular="true">
				<swiper-item v-for="(item, index) in contentImgList" :key="index">
					<image :src="item" mode="aspectFit" @click="previewpic(item,contentImgList)"></image>
				</swiper-item>
			</swiper>
		</view>

		<view class="content">
			<view class="content-base">
				<view class="content_title">{{articleDetail.title}}</view>
				<view class="content_time">{{articleTime}}</view>
				<view class="content_describe">
					<text>
						{{articleDetail.content}}
					</text>
				</view>
			</view>

			<view class="bottom">
				<view class="bottom_dianzan" @click="likeTab(started)">
					<view class="zan">
						<image :src="started?star[1]:star[0]"></image>
					</view>
					<!-- <uni-icons class="zan" type="hand-thumbsup" size="34" color="#888"></uni-icons> -->
					<view>{{articleDetail.star}}</view>
				</view>
				<view class="bottom_pinglun">
					<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
					<view>{{articleDetail.comment}}</view>
				</view>
			</view>
		</view>

		<view class="comment">

			<view class="title">评论</view>

			<view class="passage-comment" v-if="comments.length !== 0">

				<view v-for="(item, index1) in comments" @click="openCommentsDetail(item)">
					<view class="chat-navbar">
						<view class="chat-navbar-authorPhoto" @click.stop="openPersonal(item)">
							<image :src="item.mainCommentBar.avatar" mode="aspectFit"></image>
						</view>
						<view class="chat-navbar-author" @click.stop="openPersonal(item)">
							<view class="name">{{item.mainCommentBar.nickname}}</view>
							<view class="timeList">
								<view class="time">
									{{commentTime[index1]}}
								</view>
							</view>
						</view>
						<view class="head-more" @click.stop="openMenu(index1,item)">
							<uni-icons type="more-filled" size="40" color="#888"></uni-icons>
						</view>
					</view>

					<view class="comment-content">
						<view class="article_comment">
							<text>{{item.mainCommentBar.content}}</text>
							<view class="comment-content_repeat" v-if="subComment.length !== 0">
								<view class="repeat" v-for="(item, ind) in subComment[index1]" :key="ind">
									<view class="repaet_name">{{item.nickname}}：</view>
									<view class="repeat_content">{{item.content}}</view>
								</view>
								<!-- <view class="openComments" v-if="item.numOfSubComment > 2">
									展开{{item.numOfSubComment}}条回复 >
								</view> -->
							</view>
						</view>
					</view>

					<!-- <view class="bottom">
						<view class="bottom_dianzan">
							<view class="ping-zan">
								<image :src="ping_started?star[1]:star[0]"></image>
							</view> -->
							<!-- <uni-icons class="zan" type="hand-thumbsup" size="34" color="#888"></uni-icons> -->
							<!-- <view>{{item.mainCommentBar.star}}</view>
						</view>
						<view class="bottom_pinglun">
							<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
							<view>
								{{item.numOfSubComment}}
							</view>
						</view>
					</view> -->
				</view>

				<uni-load-more iconType="snow" :status="status"></uni-load-more>

			</view>

			<view class="comment-null" v-else>
				快来发表你的评论吧～
			</view>

		</view>

		<!-- 底部输入栏 -->
		<view class="input-box">
			<view class="textbox">
				<view class="text-mode">
					<view class="box">
						<textarea auto-height="true" v-model="textMsg" id="textMsg" placeholder="快来发表你的评论吧~"/>
					</view>
				</view>
			</view>
			<label for="textMsg">
				<view class="send" @tap="sendText">
					<view class="btn">
						发送
					</view>
				</view>
			</label>
		</view>
		<view style="height: 134rpx;"></view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				baseUrl: 'https://zk123.top/mye',
				articleId: '',
				articleDetail: {},
				authorDetail: {},
				contentImgList: [],
				articleTime: '',
				comments: [],
				mainComment: [],
				subComment: [],
				commentTime: [],
				status: 'more',
				textMsg: '',
				star: ["../../static/relief/star.png", "../../static/relief/star2.png"],
				started: false,
				ping_started: false,
				userInfo: {},
			}
		},
		onLoad: function(option) {
			this.articleId = option.articleId;
			var articleId = option.articleId;
			this.getArticleDetail(articleId);
			this.getComment(articleId);
			this.getUserInfo();
		},
		methods: {
			backCommunity() {
				console.log('返回社区');
				uni.navigateBack({
				    delta: 1
				});
			},
			//点赞
			likeTab(started) {
				uni.showLoading({
					title:"加载中..."
				})
				console.log('before',started);
				const _this = this;
				var is_star = _this.started;
				uni.request({
					url: 'https://zk123.top/mye/c/star',
					method: 'POST',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						id: _this.articleDetail.article_id,
						flag: '0'
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res);
						if (is_star == false) {
							_this.started = true;
							console.log('now:' + _this.started);
							console.log(_this.articleDetail.star);
							_this.articleDetail.star = _this.articleDetail.star + 1;
							uni.showToast({
								title: '点赞成功'
							})
						} else {
							_this.started = false;
							console.log('now:' + _this.started);
							console.log(_this.articleDetail.star);
							_this.articleDetail.star = _this.articleDetail.star - 1;
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
			openCommentsDetail(item) {
				console.log(item);
				console.log(item.mainCommentBar.articleId);
				const params = {
					articleId: item.mainCommentBar.articleId,
					commentId: item.mainCommentBar.commentId,
					avatar: item.mainCommentBar.avatar,
					nickname: item.mainCommentBar.nickname,
					time: item.mainCommentBar.time,
					star: item.mainCommentBar.star,
					content:item.mainCommentBar.content,
				}
				uni.navigateTo({
					//穿参注意长度
					url: "../../pages/relief/commentsDetail?params="+JSON.stringify(params)
				})
			},
			openPersonal(item) {
				var userId = item.mainCommentBar.userId;
				uni.navigateTo({
					url: "../../community-personalCenter/community-personalCenter"
					+ "?userId="
					+ userId
				})
			},
			previewpic(cind, clist) {
				uni.previewImage({
					urls: clist,
					current: cind,
					indicator: 'default'
				});
			},
			//获取帖子详细信息
			getArticleDetail(articleId) {
				uni.showLoading({
					title: "加载中..."
				})
				uni.request({
					url: this.baseUrl + '/c/article/detail', //接口地址
					method: 'GET',
					data: {
						articleId: articleId
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res.data);
						this.authorDetail = res.data.data;
						this.articleDetail = res.data.data.article;
						this.contentImgList = res.data.data.article.pic_url.split(",");
						this.articleTime = this.articleDetail.article_id.slice(6, 16);
						this.started = res.data.data.started;
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
								this.started = e.data.data;
							},
						})
					}
				});
			},
			//获取评论
			getComment(articleId) {
				uni.showLoading({
					title: "加载中..."
				})
				var _this = this;
				var h=0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/cmt',
					method: 'GET',
					data: {
						articleId: articleId,
						limit: 100,
						offset: 1
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res.data);
						var index = 0;
						_this.comments = res.data.data.comments;
						for (var i = 0; i < _this.comments.length; i++) {
							// console.log('i:',i);
							uni.request({
								url: 'https://zk123.top/mye/c/judgeStar',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									id: _this.comments[i].mainCommentBar.commentId
								},
								success: (e) => {
									// console.log(e.data.data);
									// console.log('h:',h);
									// console.log(_this.comments[h]);
									// obj[i].started = e.data.data;
									h++;
								},
							})
							_this.mainComment.push(_this.comments[i].mainCommentBar);
							_this.subComment.push(_this.comments[i].subCommentBarList);
						};
						for (var i = 0; i < _this.mainComment.length; i++) {
							_this.commentTime.push(_this.mainComment[i].time.slice(5, 16));
							for (var j = i; j < _this.commentTime.length; j++) {
								_this.commentTime[j] = _this.commentTime[j].replace(
									/(\d+)\s*-\s*(\d+)-\s*(\d+)-\s*(\d+)/, "$1-$2 $3:$4");
							}
						}
						_this.status = 'noMore';
					}
				});
			},
			// 发送文字消息
			sendText() {
				//第二个写commentid
				this.postComment(this.articleId, "0", this.textMsg);
				console.log(this.textMsg);
			},
			// 发送评论
			postComment(articleId, commentId, content) {
				uni.showLoading({
					title: "加载中..."
				})
				uni.request({
					url: this.baseUrl + '/c/leaveCmt',
					method: 'POST',
					header: {
						'content-type': 'application/x-www-form-urlencoded',
						Authorization:uni.getStorageSync('token')
					},
					data: {
						articleId: articleId,
						commentId: commentId,
						content: content,
					},
					success: (res) => {
						uni.hideLoading()
						//成功
						if (res.data.code === 200) {
							setTimeout(() => {
								// 服务端响应的 message 提示
								uni.showToast({
									title: "发送成功",
									icon: "none",
									position: 'bottom',
								})
								//延时关闭  加载中的 loading框
								uni.hideLoading()
							}, 1000)
							this.getComment(articleId);
						} else {
							//不成功
							uni.hideLoading()
							// 服务端响应的 message 提示
							setTimeout(() => {
								uni.showToast({
									title: "发送失败",
									icon: "none",
									position: 'bottom',
								})
								//延时关闭  加载中的 loading框
								uni.hideLoading()
							}, 1000)
						}
						this.textMsg = ''
					},
					fail: (res) => {
						// 服务端响应的 message 提示
						setTimeout(() => {
							uni.showToast({
								title: "发送失败",
								icon: "none",
								position: 'bottom',
							})
							//延时关闭  加载中的 loading框
							uni.hideLoading()
						}, 1000)
					}
				})
			},
			openMenu(index1, item) {
				var _this = this;
				// 评论的userId  数据的类型是string 需要先转换一下数据类型
				var comment_userId = item.mainCommentBar.userId;
				var usrId = _this.userInfo.id;
				// 看是否为自己发的帖子
				if (_this.userInfo.id === comment_userId) {
					uni.showActionSheet({
						itemList: ['删除评论', '复制内容'],
						success: function(res) {
							var index = res.tapIndex;
							if (index === 0) {
								_this.deleteArticle(index1, item);
							} else if (index === 1) {
								console.log('复制内容');
								uni.showToast({
									title: '评论内容已复制'
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
									title: '评论内容已复制'
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
				console.log(item.mainCommentBar.commentId);
				uni.showModal({
					title: '友情提示',
					content: '确定要删除这条评论吗',
					success: function(res) {
						if (res.confirm) {
							uni.request({
								url: 'https://zk123.top/mye/c/cmt/delete',
								method: 'POST',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									commentId: item.mainCommentBar.commentId,
								},
								success: (res) => {
									console.log(index);
									_this.comments.splice(index, 1);
									_this.subComment.splice(index, 1);
									_this.commentTime.splice(index, 1);
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
					}
				})
			},
		}
	}
</script>

<style lang="scss">
	.community-passage {
		background: #FFFFFF;
	}

	.navbar .navbar-fixed {
		display: flex;
		justify-content: start;
		align-items: center;
		width: 100%;
		height: 50px;
		background-color: #FFFFFF;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 99;
		text-align: center;
		padding-top: 4rpx;
		padding-bottom: 4rpx;

		.navbar-return {
			margin: 20rpx;
			margin-right: 40rpx;
		}

		.navbar-authorPhoto image {
			width: 60rpx;
			height: 60rpx;
			border-radius: 50%;
		}

		.navbar-authorName {
			font-size: 30rpx;
			margin: 20rpx;
			color: #555555;
		}
	}

	.image {
		swiper {
			width: 750rpx;
			height: 500rpx;

			image {
				width: 100%;
				height: 100%;
			}
		}
	}

	.content {
		display: flex;
		flex-direction: column;
		padding: 20rpx;
		margin: 10rpx;

		.content_title {
			font-size: 32rpx;
			color: #333333;
			font-weight: 400;
			line-height: 1.8;
			padding-bottom: 10rpx;
		}

		.content_time {
			font-size: 24rpx;
			color: #999;
			line-height: 1.8;
			padding-bottom: 10rpx;
		}

		.content_describe {
			font-size: 30rpx;
			color: #555;
			line-height: 1.8;
		}
	}

	.content .bottom {
		border-bottom: 1px solid #CCCCCC;
		display: flex;
		float: left;
		width: 100%;
		height: 75rpx;
		justify-content: flex-end;
		align-items: center;
		position: relative;
		font-size: 28rpx;
		color: #888888;

		.bottom_dianzan {
			display: flex;
			position: absolute;
			right: 130rpx;

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

		.bottom_pinglun {
			display: flex;
			padding-right: 20rpx;

			.ping {
				margin-top: 5rpx;
				padding-right: 10rpx;
			}
		}
	}

	.comment {
		display: flex;
		flex-direction: column;
		padding: 20rpx;
		margin: 10rpx;

		.title {
			font-size: 30rpx;
			border-left: 2px solid #007AFF;
			padding-left: 20rpx;
			color: #666;
		}

		.comment-null {
			display: flex;
			flex-direction: column;
			height: 200rpx;
			align-items: center;
			justify-content: center;
			color: #666;
			font-size: 30rpx;
		}
	}

	.passage-comment {
		display: flex;
		flex-direction: column;
		margin: 20rpx 0;

		.chat-navbar {
			display: flex;
			justify-content: start;
			align-items: center;
			height: 100rpx;
			padding: 4rpx 0;
			position: relative;

			.chat-navbar-authorPhoto image {
				margin: 10rpx 0;
				margin-right: 10rpx;
				width: 60rpx;
				height: 60rpx;
				border-radius: 50%;
			}

			.chat-navbar-author {
				margin: 10rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;

				.name {
					font-size: 30rpx;
					color: #555;
				}

				.timeList {
					display: flex;
					flex-direction: row;

					.time {
						display: flex;
						flex-direction: row;
						font-size: 24rpx;
						color: #999;
					}
				}
			}
			
			.head-more {
				position: absolute;
				right: 25rpx;
			}
		}

		.comment-content {
			display: flex;
			flex-direction: row;
			flex-wrap: wrap;
			font-size: 30rpx;
			line-height: 1.6;
			color: #555;
			margin-left: 70rpx;
			margin-right: 10rpx;

			.comment-content_repeat {
				display: flex;
				flex-direction: column;
				box-sizing: border-box;
				margin: 10rpx 0;
				font-size: 30rpx;
				line-height: 1.6;
				color: #555;
				width: 620rpx;
				background: #F5F5F5;
				border-radius: 14rpx;

				.repeat {
					display: flex;
					padding: 10rpx 20rpx;
					flex-wrap: wrap;

					.repaet_name {
						color: #333;
					}
				}

				.openComments {
					padding: 10rpx 20rpx;
					font-size: 28rpx;
					color: #22a3d0;
					display: flex;
					justify-content: flex-end;
				}
			}
		}

		.bottom {
			border-bottom: 1px solid #CCCCCC;
			display: flex;
			float: left;
			width: 610rpx;
			margin-left: 70rpx;
			height: 75rpx;
			justify-content: flex-end;
			align-items: center;
			position: relative;
			font-size: 28rpx;
			color: #888888;

			.bottom_dianzan {
				display: flex;
				position: absolute;
				right: 130rpx;

				.ping-zan {
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

			.bottom_pinglun {
				display: flex;
				padding-right: 20rpx;

				.ping {
					margin-top: 5rpx;
					padding-right: 10rpx;
				}
			}
		}

	}

	.input-box {
		width: 100%;
		min-height: 100upx;
		padding: 0 1%;
		background-color: #f2f2f2;
		display: flex;
		position: fixed;
		z-index: 20;
		bottom: 0;

		.send {
			margin-left: 20upx;
			flex-shrink: 0;
			width: 100upx;
			height: 100upx;
			display: flex;
			align-items: center;

			.btn {
				width: 90upx;
				height: 56upx;
				display: flex;
				justify-content: center;
				align-items: center;
				background: #00a3d6;
				color: #fff;
				border-radius: 6upx;
				font-size: 24upx;
			}
		}

		.textbox {
			width: 100%;
			min-height: 70upx;
			margin-top: 15upx;

			.text-mode {
				width: 100%;
				min-height: 70upx;
				display: flex;
				background-color: #fff;
				border-radius: 40upx;

				.box {
					width: 100%;
					padding-left: 30upx;
					min-height: 70upx;
					display: flex;
					align-items: center;

					textarea {
						width: 100%;
						font-size: 30rpx;
					}
				}

				.em {
					flex-shrink: 0;
					width: 80upx;
					height: 70upx;
					display: flex;
					justify-content: center;
					align-items: center;
				}
			}
		}
	}

</style>
