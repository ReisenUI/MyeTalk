<template>
	<view>

		<view class="image">
			<swiper indicator-dots="true" circular="true">
				<swiper-item v-for="item in items">
					<image :src="item.pic" mode="aspectFill"></image>
				</swiper-item>
			</swiper>
		</view>

		<view class="content">
			<view class="content-base">
				<view class="content_title">请回答1988</view>
				<view class="content_time">2021.3.26</view>
				<view class="content_describe">
					<text>
						<!-- 故事真的就是《你眼中的世界》+《只有你听见》，但我们就是会被这种自己终其一生都不会遇见的奇迹所感动啊！ -->
						德善真的太可爱了～这个女孩谁不爱～
					</text>
				</view>
			</view>

			<view class="bottom">
				<view class="bottom_dianzan">
					<uni-icons class="zan" type="hand-thumbsup" size="34" color="#888"></uni-icons>
					<view>16</view>
				</view>
				<view class="bottom_pinglun">
					<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
					<view>3</view>
				</view>
			</view>
		</view>


	</view>
</template>

<script>
	export default {
		name: "passage-content",
		data() {
			return {
				items: [{
					pic: '../../static/head_photo/4.jpg'
				}, {
					pic: '../../static/head_photo/deshan-2.jpg'
				}]
			};
		}
	}
</script>

<style lang="scss">
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
			font-size: 28rpx;
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


</style>
