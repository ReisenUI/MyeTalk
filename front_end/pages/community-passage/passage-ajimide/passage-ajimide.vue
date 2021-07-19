<template>
	<view class="community-passage">
		
		<view class="navbar">
			<view class="navbar-fixed">
				<view class="navbar-return" @click="backToCommunity">
					<uni-icons type="arrowleft" size="50" color="#999" @click="navigateBack"></uni-icons>
				</view>
				<view class="navbar-authorPhoto">
					<image src="../../../static/head_photo/mye.png" mode="aspectFit"></image>
				</view>
				<view class="navbar-authorName">
					MYE
				</view>
			</view>
			<view style="height: 54px;"></view>
		</view>
		
		<view class="image">
			<swiper indicator-dots="true" circular="true">
				<swiper-item v-for="item in items">
					<image :src="item.pic" mode="aspectFill"></image>
				</swiper-item>
			</swiper>
		</view>
		
		<view class="content">
			<view class="content-base">
				<view class="content_title">阿基米德专区介绍和发帖须知</view>
				<view class="content_time">2021.3.26</view>
				<view class="content_describe">
					<text>
						以下：
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;阿基米德专区是为了给大家提供一个宣泄情绪和释放压力的地方而创立的，在这里，你可以积极地与他人交换意见，表达对某个热门话题的看法，或者静静地独自一人发表宣泄的帖子，但也希望你能明白，在阿基米德专区内的帖子和评论只对事不对人。
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在这里，以下话题是被禁止的：
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、政治一类的敏感话题
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、人身攻击、地域攻击
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、......
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;阿基米德专区在每周一、周五开放，届时删除以往发布的任何帖子，也由此希望各位能将释放过的戾气和和对生活的不满抛在脑后，以全新的、积极的心态面对每个明天。
					</text>
				</view>
			</view>
		
			<!-- <view class="bottom">
				<view class="bottom_dianzan">
					<uni-icons class="zan" type="hand-thumbsup" size="34" color="#888"></uni-icons>
					<view>4</view>
				</view>
				<view class="bottom_pinglun">
					<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
					<view>3</view>
				</view>
			</view> -->
		</view>
		
		<!-- <view class="comment">
			
			<view class="title">评论</view>
			
			<view class="passage-comment">
				
				<view class="chat-navbar mode_comment_two">
					<view class="chat-navbar-fixed">
						<view class="chat-navbar-authorPhoto">
							<image src="../../../static/head_photo/1.jpg" mode="aspectFit"></image>
						</view>
						<view class="chat-navbar-author">
							<view class="name">西柚子茶</view>
							<view class="time">03-28 09:10</view>
						</view>
					</view>
					
					<view class="comment-content">
						哈哈，这就是传说中的杠精专区吗（狗头）
					</view>
					
					<view class="comment-content_repeat">
						<view class="repaet_name">MYE(作者): &nbsp;</view>
						<view class="repeat_connet">哈哈被你发现了</view>
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
				
				<view class="chat-navbar">
					<view class="chat-navbar-fixed">
						<view class="chat-navbar-authorPhoto">
							<image src="../../../static/head_photo/zhang.jpeg" mode="aspectFit"></image>
						</view>
						<view class="chat-navbar-author">
							<view class="name">季夏三日月</view>
							<view class="time">03-26 20:10</view>
						</view>
					</view>
					
					<view class="comment-content">
						好家伙，这专区我直呼好家伙
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
				
				
			</view>
			
		</view> -->
		
		<!-- <view class="chat" @click="open">
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
		<view style="height: 134rpx;"></view> -->

	</view>
</template>

<script>
	export default {
		data() {
			return {
				items: [{
					pic: '../../../static/img-community/ajimide.jpg'
				}]
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
			},
			backToCommunity() {
				uni.navigateBack();
			}
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
	}
	.navbar .navbar-fixed .navbar-return {
		margin: 20rpx;
		margin-right: 40rpx;
	}
	
	.navbar .navbar-fixed .navbar-authorPhoto image {
		width: 60rpx;
		height: 60rpx;
		border-radius: 50%;
	}
	
	.navbar .navbar-fixed .navbar-authorName {
		font-size: 30rpx;
		margin: 20rpx;
		color: #555555;
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
			font-size: 32rpx;
			border-left: 2px solid #007AFF;
			padding-left: 20rpx;
			color: #666;
		}
	}
	
	.passage-comment {
		display: flex;
		flex-direction: column;
		
		.chat-navbar {
			margin: 20rpx 0;
			.chat-navbar-fixed {
				display: flex;
				justify-content: start;
				align-items: center;
				height: 100rpx;
				padding: 4rpx 0;
				
				.chat-navbar-authorPhoto image{
					margin: 10rpx 0;
					margin-right: 10rpx;
					width: 60rpx;
					height: 60rpx;
					border-radius: 50%;
				}
				
				.chat-navbar-author {
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
			
			.comment-content {
				display: flex;
				float: left;
				width: 610rpx;
				font-size: 30rpx;
				line-height: 1.6;
				color: #555;
				margin-left: 70rpx;
			}
			
			.comment-content_repeat {
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
		border-radius: 30rpx;
		background-color: #f5f5f5;
		font-size: 30rpx;
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
			margin: 34rpx 0;
			padding: 12rpx 0;
			color: #555;
		}
	}
</style>
