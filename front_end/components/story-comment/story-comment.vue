<template>
	<view>
		<view class="chatMessage">
			<view class="chatPerson">
				<view class="chatPerson-photo">
					<image src="/static/head_photo/1.jpg" mode="aspectFit"></image>
				</view>
				<view class="chatPerson-info">
					<view class="name">西柚子茶</view>
					<view class="time">评论了你的故事 03-28 09:10</view>
				</view>
			</view>
			
			<view class="chatContent">
				只要你喜欢她，咱俩就是好朋友
			</view>
			
			<view class="communityPassage">
				<view class="passageAuthor">@ReisenU</view>
				<view class="passageTitle">#卡哇伊#</view>
				<view class="passageContent">
					这也太可爱了吧！谁挡得住啊！
				</view>
			</view>
		</view>
		
		<view class="chatMessage">
			<view class="chatPerson">
				<view class="chatPerson-photo">
					<image src="/static/head_photo/zhang.jpeg" mode="aspectFit"></image>
				</view>
				<view class="chatPerson-info">
					<view class="name">季夏三日月</view>
					<view class="time">评论了你的故事 03-26 20:10</view>
				</view>
			</view>
			
			<view class="chatContent">
				集美们，发现宝藏了
			</view>
			
			<view class="communityPassage">
				<view class="passageAuthor">@ReisenU</view>
				<view class="passageTitle">#卡哇伊#</view>
				<view class="passageContent">
					这也太可爱了吧！谁挡得住啊！
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name:"story-comment",
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="scss">
	.chatMessage {
		background: #FFFFFF;
		display: flex;
		flex-direction: column;
		padding: 30rpx 40rpx;
		margin: 16rpx 0;
		border-bottom: 1px solid #f5f5f5;
		.chatPerson {
			display: flex;
			height: 100rpx;
			padding: 4rpx 0;
			align-items: center;
			.chatPerson-photo image {
				margin: 10rpx 0;
				margin-right: 10rpx;
				width: 60rpx;
				height: 60rpx;
				border-radius: 50%;
			}
			.chatPerson-info {
				margin: 10rpx;
				.name {
					font-size: 30rpx;
					color: #000;
				}
				.time {
					font-size: 24rpx;
					color: #999;
				}
			}
		}
		.chatContent {
			display: flex;
			float: left;
			font-size: 30rpx;
			line-height: 1.6;
			color: #555;
			margin: 10rpx 0;
		}
		.communityPassage {
			box-sizing: border-box;
			font-size: 30rpx;
			padding: 16rpx;
			line-height: 1.7;
			color: #777;
			background: #F5F5F5;
			border-radius: 14rpx;
			.passageAuthor {
				color: #000;
			}
		}
	}
</style>

