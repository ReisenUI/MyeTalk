<template>

	<view class="navbar">
		<view class="navbar-fixed">
			<view class="navbar-return" @click="navigateBack">
				<uni-icons type="arrowleft" size="50" color="#999" @click="navigateBack"></uni-icons>
			</view>
			<view class="navbar-authorPhoto">
				<image src="../../static/head_photo/zhang.jpeg" mode="aspectFit"></image>
			</view>
			<view class="navbar-authorName">
				季夏三日月
			</view>
		</view>
		<view style="height: 54px;"></view>
		<!-- <view style="height: 75px;"></view> -->
	</view>

</template>

<script>
	export default {
		name: "passage-author",
		data() {
			return {
				
			};
		},
		methods: {
			navigateBack() {
				uni.navigateBack();
			}
		}
	}
</script>

<style>
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
</style>
