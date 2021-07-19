<template>
	<view>
		<view class="comment">
			<view class="chat-navbar">
				<view class="chat-navbar-authorPhoto">
					<image :src="formData.avatar" mode="aspectFit"></image>
				</view>
				<view class="chat-navbar-author">
					<view class="name">{{formData.nickname}}</view>
					<view class="time">{{formData.time}}</view>
				</view>
				<view class="zan">
					<uni-icons type="hand-thumbsup" size="34" color="#888"></uni-icons>
					<view class="zan-number">
						{{formData.star}}
					</view>
				</view>
			</view>

			<view class="comment-repeat-content">
				<text>
					{{formData.content}}
				</text>
			</view>
		</view>

		<view v-if="subCommentList.length !== 0">
			<view class="repeat">
				<view class="comment-repeat" v-for="(item,index) in subCommentList" :key="index">
					<view class="chat-navbar">
						<view class="chat-navbar-authorPhoto">
							<image :src="item.avatar" mode="aspectFit"></image>
						</view>
						<view class="chat-navbar-author">
							<view class="name">{{item.nickname}}</view>
							<view class="time">{{commentTime[index]}}</view>
						</view>
					</view>

					<view class="comment-repeat-content">
						<view class="repeat-name">
							回复 {{item.p2_nickname}}：
						</view>
						<text>
							{{item.content}}
						</text>
					</view>
				</view>
			</view>
		</view>

		<view class="comment-null" v-else>
			快来回复楼主的评论吧～
		</view>

		<!-- 底部输入栏 -->
		<view class="input-box">
			<view class="textbox">
				<view class="text-mode">
					<view class="box">
						<!--  :placeholder="formData.nickname" -->
						<textarea auto-height="true" v-model="textMsg" id="textMsg" placeholder="回复楼主: " />
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
				formData: {},
				subCommentList: [],
				commentTime: [],
				commentId: '',
				articleId: '',
				textMsg: '',
				mainCommentTime:[],
			}
		},
		onLoad: function(option) {
			this.formData = JSON.parse(option.params);
			var commentId = this.formData.commentId;
			var articleId = this.formData.articleId;
			console.log(articleId);
			this.getCommentDetail(commentId);
		},
		methods: {
			getCommentDetail(commentId) {
				uni.showLoading({
					title: "加载中..."
				})
				uni.request({
					url: this.baseUrl + '/c/cmt/detail',
					method: 'GET',
					data: {
						commentId: commentId,
						limit: 100,
						offset: 1
					},
					success: (res) => {
						uni.hideLoading();
						var _this = this;
						console.log(res.data);
						_this.subCommentList = res.data.data.subCommentList;
						for (var i = 0; i < _this.subCommentList.length; i++) {
							_this.commentTime.push(_this.subCommentList[i].time.slice(5, 16));
							for (var j = i; j < _this.commentTime.length; j++) {
								_this.commentTime[j] = _this.commentTime[j].replace(
									/(\d+)\s*-\s*(\d+)-\s*(\d+)-\s*(\d+)/, "$1-$2 $3:$4");
							}
						}
					}
				});
			},
			// 发送文字消息
			sendText() {
				this.postComment(this.formData.articleId, this.formData.commentId, this.textMsg);
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
						Authorization: uni.getStorageSync('token')
					},
					data: {
						articleId: this.formData.articleId,
						commentId: this.formData.commentId,
						content: content,
					},
					success: (res) => {
						//成功
						uni.hideLoading();
						if (res.data.code === 200) {
							setTimeout(() => {
								// 服务端响应的 message 提示
								uni.showToast({
									title: "发送成功",
									icon: "none",
									position: 'bottom',
								})
							}, 1000)
							this.getCommentDetail(this.formData.commentId);
						} else {
							// 服务端响应的 message 提示
							uni.hideLoading()
							setTimeout(() => {
								uni.showToast({
									title: "发送失败",
									icon: "none",
									position: 'bottom',
								})
							}, 1000)
						}
						this.textMsg = ''
					},
					fail: (res) => {
						// 服务端响应的 message 提示
						uni.hideLoading()
						setTimeout(() => {
							uni.showToast({
								title: "发送失败",
								icon: "none",
								position: 'bottom',
							})
						}, 1000)
					}
				})
			},
		}
	}
</script>

<style lang="scss">
	.comment {
		background-color: #FFFFFF;
		padding: 10rpx 20rpx 10rpx 30rpx;
	}

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

			.time {
				display: flex;
				flex-direction: row;
				font-size: 24rpx;
				color: #999;
			}
		}

		.zan {
			position: absolute;
			right: 20rpx;
			display: flex;
			flex-direction: row;

			.zan-number {
				margin-left: 10rpx;
				color: #666;
			}
		}
	}

	.comment-repeat-content {
		margin: 0 10rpx 10rpx 70rpx;
		box-sizing: border-box;
		padding-bottom: 30rpx;
		color: #666;
		line-height: 1.6;
	}

	.repeat {
		padding-bottom: 60rpx;

		.comment-repeat {
			padding: 10rpx 20rpx 10rpx 30rpx;

			.comment-repeat-content {
				display: flex;
				flex-wrap: wrap;
				border-bottom: 1px solid #eee;

				.repeat-name {
					color: #444;
				}
			}
		}
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

	.chat {
		display: flex;
		width: 100%;
		height: 90rpx;
		background-color: #FFFFFF;
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 99;
		border-top: 1px solid #f3f3f3;
		align-items: center;
		justify-content: center;
		padding: 10rpx 0;

		.chat-fixed {
			display: flex;
			align-items: center;
			width: 86%;
			height: 70rpx;
			line-height: 70rpx;
			padding-left: 30rpx;
			border-radius: 40rpx;
			background-color: #f5f5f5;
			font-size: 30rpx;
			color: #777;
		}
	}

	.popup-wrap {
		background: #FFFFFF;
		display: flex;
		justify-content: space-between;
		font-size: 30rpx;

		.popup-content {
			height: 150rpx;
			margin: 20rpx;
			margin-right: 0rpx;
			align-items: center;
			justify-content: center;

			.chat_selected {
				background-color: #f5f5f5;
				width: 600rpx;
				height: 130rpx;
				padding-top: 20rpx;
				padding-left: 20rpx;
				padding-top: 20rpx;
				border-radius: 30rpx;

				.chat-fixed_selected {
					font-size: 30rpx;
				}
			}
		}

		.popup-item {
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			font-size: 30rpx;
			width: 100rpx;
			text-align: center;
			margin: 16rpx 0;
			padding: 12rpx 0;
			color: #555;
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
