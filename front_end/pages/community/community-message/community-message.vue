<template>
	<view>
		<view class="box-chat-comment">
			<view class="chat" @click="openChat()">
				<uni-icons type="chatbubble" size="40"></uni-icons>
				<view class="chat-info">评论</view>
				<uni-icons type="forward" size="30"></uni-icons>
			</view>

			<!-- <view class="comment" @click="openComment()">
				<uni-icons type="sound" size="40"></uni-icons>
				<view class="chat-info">点赞</view>
				<uni-icons type="forward" size="30"></uni-icons>
			</view> -->
		</view>

		<navigator url="../../community-passage/passage-ajimide/passage-ajimide">
			<view class="activity">
				<view class="activity-title">
					阿基米德专区介绍和发帖须知
				</view>
				<view class="activity-info">
					<text>
						阿基米德专区是为了给大家提供一个宣泄情绪和释放压力的地方而创立的，在这里，你可以积极地与他人交换意见，表达对某个热门话题的看法，或者静静地独自一人发表宣泄的帖子，但也希望你能明白，在阿基米德专区内的帖子和评论只对事不对人。
					</text>
				</view>
				<view class="activity-bottom">
					<view class="bottom-time">2021.3.29</view>
					<view class="bottom-info">查看详情 ></view>
				</view>
			</view>
		</navigator>

	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			openChat() {
				uni.navigateTo({
					url: "../message-chat/message-chat"
				})
			},
			openComment() {
				uni.navigateTo({
					url: "../message-like/message-like"
				})
			},
		}
	}
</script>

<style lang="scss">
	.box-chat-comment {
		background: #FFFFFF;
		display: flex;
		flex-direction: column;
		padding: 0rpx 30rpx;
		color: #06181f;

		.chat,
		.comment {
			display: flex;
			align-items: center;
			font-size: 30rpx;
			justify-content: space-between;
			padding: 30rpx 10rpx;
			position: relative;

			.chat-info {
				position: absolute;
				left: 80rpx;
			}
		}

		.chat {
			border-bottom: 1px solid #efefef;
		}
	}

	.activity {
		background: #FFFFFF;
		margin-top: 20rpx;
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		padding: 20rpx 30rpx;

		.activity-title {
			padding: 10rpx;
			font-size: 32rpx;
			font-weight: 400;
			color: #000000;
		}

		.activity-info {
			padding: 10rpx;
			color: #4a4a4a;
			font-size: 30rpx;

			text {
				overflow: hidden;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 2;
				-webkit-box-orient: vertical;
			}
		}

		.activity-bottom {
			padding: 10rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;

			.bottom-time {
				font-size: 24rpx;
				color: #999;
			}

			.bottom-info {
				font-size: 28rpx;
				color: #22a3d0;
			}
		}
	}
</style>
