<template>
	<view class="passage-comment">
				
		<view v-if="mode === 'one'" class="navbar">
			<view class="navbar-fixed">
				<view class="navbar-authorPhoto">
					<image src="../../static/head_photo/zhang.jpeg" mode="aspectFit"></image>
				</view>
				<view class="navbar-author">
					<view class="name">季夏三日月</view>
					<view class="time">03-26 20:10</view>
				</view>
			</view>
			
			<view class="content">
				集美们，快去看啊！入股不亏～
			</view>
			
			<view class="bottom">
				<view class="bottom_dianzan">
					<uni-icons class="zan" type="hand-thumbsup" size="34" color="#888"></uni-icons>
					<view>1</view>
				</view>
				<view class="bottom_pinglun">
					<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
					<view>0</view>
				</view>
			</view>
		</view>
		
		
		<view v-if="mode === 'two'" class="navbar mode_comment_two">
			<view class="navbar-fixed">
				<view class="navbar-authorPhoto">
					<image src="../../static/head_photo/1.jpg" mode="aspectFit"></image>
				</view>
				<view class="navbar-author">
					<view class="name">西柚子茶</view>
					<view class="time">03-28 09:10</view>
				</view>
			</view>
			
			<view class="content">
				只要你喜欢她，咱俩就是好朋友
			</view>
			
			<view class="content_repeat">
				<view class="repaet_name">季夏三日月(作者): &nbsp;</view>
				<view class="repeat_connet">哈哈亲故</view>
			</view>
			
			<view class="bottom">
				<view class="bottom_dianzan">
					<uni-icons class="zan" type="hand-thumbsup" size="34" color="#888"></uni-icons>
					<view>1</view>
				</view>
				<view class="bottom_pinglun">
					<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
					<view>1</view>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		name:"passage-comment",
		props: {
			mode: {
				type: String,
				default:'base'
			}
		},
		data() {
			return {
				items: [
					{
						name:'季夏三日月',
						time:'03.26 20:10',
						content: '集美们，快去看啊！入股不亏～',
						likes: 16,
						comments: 3
					},
					{
						name:'季夏三日月',
						time:'03.26 20:10',
						content: '集美们，快去看啊！入股不亏～',
						likes: 16,
						comments: 3
					},
					{
						name:'季夏三日月',
						time:'03.26 20:10',
						content: '集美们，快去看啊！入股不亏～',
						likes: 16,
						comments: 3
					},
				]
			};
		}
	}
</script>

<style lang="scss">
	.passage-comment {
		display: flex;
		flex-direction: column;
		
		.navbar {
			margin: 20rpx 0;
			.navbar-fixed {
				display: flex;
				justify-content: start;
				align-items: center;
				height: 100rpx;
				// text-align: center;
				padding: 4rpx 0;
				
				.navbar-authorPhoto image{
					margin: 10rpx 0;
					margin-right: 10rpx;
					width: 60rpx;
					height: 60rpx;
					border-radius: 50%;
				}
				
				.navbar-author {
					margin: 10rpx;
					.name {
						font-size: 30rpx;
						color: #555;
					}
					.time {
						font-size: 24rpx;
						color: #999;
					}
				}
			}
			
			.content {
				display: flex;
				float: left;
				width: 610rpx;
				font-size: 30rpx;
				line-height: 1.6;
				color: #555;
				margin-left: 70rpx;
			}
			
			.content_repeat {
				display: flex;
				float: left;
				flex-wrap: nowrap;
				color: #555;
				box-sizing: border-box;
				font-size: 30rpx;
				width: 600rpx;
				margin: 10rpx 70rpx 0;
				padding: 10rpx 30rpx;
				line-height: 1.6;
				background: #F5F5F5;
				border-radius: 10rpx;
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
				
					.zan {
						margin-top: 2rpx;
						padding-right: 10rpx;
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
		
	}
	
</style>
