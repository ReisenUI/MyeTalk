<template>
	<view class="community-passage">
		<passage-author></passage-author>
		<passage-content></passage-content>
		
		<view class="comment">
			
			<view class="title">评论</view>
			
			<view>
				<passage-comment mode="two"></passage-comment>
				<passage-comment mode="one"></passage-comment>
			</view>
			
		</view>
		
		<view class="chat" @click="open">
			<textarea class="chat-fixed" placeholder="说点什么吧......" />
		</view>

		<uni-popup ref="popup" type="bottom">
			<view class="popup-wrap">
				<view class="popup-content">
					<view class="chat_selected">
						<textarea class="chat-fixed_selected" placeholder="说点什么吧......" />
					</view>
				</view>
				
				<view class="popup-item">
					<text class="popup-item_close" @click="close">取消</text>
					<text class="popup-item_submit" @click="submit">发送</text>
				</view>
			</view>
		</uni-popup>
		<view style="height: 114rpx; "></view>

	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			open() {
				//通过组件定义的ref调用uni-popup方法
				this.$refs.popup.open()
			},
			close() {
				this.$refs.popup.close()
			},
			submit() {
				console.log('发布')
				this.$refs.popup.close()
			}
		}
	}
</script>

<style lang="scss">
	.community-passage {
		background: #FFFFFF;
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
	}

	.chat .chat-fixed {
		display: flex;
		align-items: center;
		width: 86%;
		height: 70rpx;
		line-height: 70rpx;
		padding-left: 30rpx;
		border-radius: 40rpx;
		background-color: #f5f5f5;
		font-size: 30rpx;
		box-sizing: border-box;
		color: #777;
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
			margin: 14rpx 0;
			padding: 12rpx 0;
			color: #555;
		}
	}
</style>
