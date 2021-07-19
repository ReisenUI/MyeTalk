<template>
	<view class="root">
		<view class="card1" @tap='gotocard1'>
			<text class="title">看看你最近的事业运势 </text>
		</view>
		<view class="card2" @tap='gotocard2'>
			<text class="title">你有富婆命吗 </text>
		</view>
		<view class="card3" @tap='gotocard3'>
			<text class="title">你会在几岁结婚 </text>
		</view>
		<view class="card4" @tap='gotocard4'>
			<text class="title">你未来十年有多富有 </text>
		</view>
	</view>
</template>

<script>
	export default {
		methods: {
			gotocard1() {
				uni.navigateTo({
					url: '/pages/relief/card/card1'
				})
			},
			gotocard2() {
				uni.navigateTo({
					url: '/pages/relief/card/card2'
				})
			},
			gotocard3() {
				uni.navigateTo({
					url: '/pages/relief/card/card3'
				})
			},

			gotocard4() {
				uni.navigateTo({
					url: '/pages/relief/card/card4'
				})
			}
		}
	}
</script>

<style>
	page {
		background: url('https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1616785550406-0fd04cac3c24?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjh8fHRhcm90fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80');
	}

	.root {
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
	}

	.card1 {
		width: 75%;
		height: 300rpx;
		background: url('https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1568821137008-6a947a8decfc?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHRhcm90fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80');
		background-size: 100% auto;
		margin-left: 30rpx;
		margin-top: 20rpx;
		border-radius: 30rpx;
		font-size: 50rpx;
		color: #fff;
		font-weight: 500;
		font-style: italic;
		text-align: center;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.card2 {
		width: 75%;
		height: 300rpx;
		background: url('https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1556739442-4c892bcbe8ba?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fHRhcm90fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80');
		background-size: 100% auto;
		margin-left: 160rpx;
		margin-top: 20rpx;
		border-radius: 30rpx;
		font-size: 50rpx;
		color: #fff;
		font-weight: 500;
		font-style: italic;
		text-align: center;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.card3 {
		width: 75%;
		height: 300rpx;
		background: url('https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1558467778-122dbb038e3b?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fHRhcm90fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80');
		background-size: 100% auto;
		margin-left: 30rpx;
		margin-top: 20rpx;
		border-radius: 30rpx;
		font-size: 50rpx;
		color: #fff;
		font-weight: 500;
		font-style: italic;
		text-align: center;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.card4 {
		width: 75%;
		height: 300rpx;
		background: url('https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1615710468024-240c22864fe3?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDJ8fHRhcm90fGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80');
		background-size: 100% auto;
		margin-left: 160rpx;
		margin-top: 20rpx;
		border-radius: 30rpx;
		font-size: 50rpx;
		color: #fff;
		font-weight: 500;
		font-style: italic;
		text-align: center;
		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>
