<template>
	<view class="root">

		<view v-for="(item,index) in listitem">
			<view class="view1">
				<view class="view2">
					<image class="image1" :src="[item.headhpoto]"></image>
					<text>{{item.name}}</text>
				</view>
				<view class="view3">
					<image :src="[item.picture]" class="image2"></image>
				</view>
				<view class="view4">
					<text>{{item.time}}</text>
					<view class="view41">
						<image src="../static/star.png"></image>
						<text>{{item.starnum}}</text>
					</view>
					<view class="view42">
						<image src="../static/comments.png"></image>
						<text>{{item.comments}}</text>
					</view>
				</view>

			</view>


		</view>
	</view>
</template>

<script>
	export default {
		//name: "list",
		data() {
			return {
				listitem: [{
						headhpoto:'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2645058785,2214371962&fm=26&gp=0.jpg',
						name: 'Black Angela',
						picture: 'https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2346322522,3743951396&fm=26&gp=0.jpg',
						time: '1小时前',
						starnum: 12,
						comments: 7,
					},
					{
						headhpoto: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2645058785,2214371962&fm=26&gp=0.jpg',
						name: 'Black Angela',
						picture: 'https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=464760846,186589948&fm=26&gp=0.jpg',
						time: '1小时前',
						starnum: 22,
						comments: 9,
					},
					{
						headhpoto: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2645058785,2214371962&fm=26&gp=0.jpg',
						name:'Black Angela',
						picture: 'https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3205518759,2115189970&fm=26&gp=0.jpg',
						time: '2小时前',
						starnum: 28,
						comments: 16,
					},
					{
						headhpoto: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2645058785,2214371962&fm=26&gp=0.jpg',
						name: 'Black Angela',
						picture: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3491557058,3936895789&fm=26&gp=0.jpg',
						time: '2小时前',
						starnum: 32,
						comments: 18,
					},
					{
						headhpoto: 'https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2645058785,2214371962&fm=26&gp=0.jpg',
						name: 'Black Angela',
						picture: 'https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1452199210,2356085428&fm=26&gp=0.jpg',
						time: '3小时前',
						starnum: 50,
						comments: 32,
					}
				]






			};
		}
	}
</script>

<style>
	.root {
		width: 100%;
		height: 100%;
	}

	.view1 {
		height: 684rpx;
		width: 100%;
		/* margin: 4rpx 3%; */
		background: #ffffff;
		display: flex;
		flex-direction: column;
		margin: 4rpx 0;
	}

	.view2 {
		height: 114rpx;
		width: 100%;
		/* background: yellow; */
		display: flex;
		/* flex-direction: row; */
		align-items: center;

	}

	.view2 text {
		font-size: 18px;
		color: #757571;
		/* background: #007AFF; */
		/* 		margin-left: 20rpx; */
		line-height: 50rpx;
		/* 	margin-top: 32rpx; */
		margin: 32rpx 20rpx;
	}

	.view3 {
		width: 100%;
		height: 480rpx;
		/* margin: 10rpx 0; */
		/* background: #007AFF; */
	}

	.image2 {
		width: 100%;
		height: 480rpx;

	}

	.view4 {
		width: 100%;
		height: 90rpx;
		display: flex;
		flex-direction: row;
		/* align-items: center; */
		/* background: #3F536E; */
	}

	.view4 text {
		/* background: #007AFF; */
		margin-top: 10rpx;
		margin-left: 20rpx;
		font-size: 20rpx;
		color: #757571;
	}

	.view41 {
		margin-top: 10rpx;
		margin-left: 300rpx;
		margin-right: 30rpx;
		width: 120rpx;
		height: 60rpx;
		/* background: #007AFF; */
		display: flex;
		align-items: center;
	}

	.view41 image {
		width: 50rpx;
		height: 50rpx;
		margin: 6rpx 0;
		/* background: #4CD964; */
		/* margin: 5rpx; */

		/* margin-right: 0rpx; */
	}

	.view41 text {
		margin:50rpx 0;
		/* background: red; */
	}
.view42 text {
		margin:50rpx 4rpx;
		/* background: red; */
	}

	.view42 {
		margin-top: 10rpx;
		display: flex;
		align-items: center;
		width: 120rpx;
		height: 60rpx;
		/* background: #007AFF; */
	}

	.view42 image {
		width: 48rpx;
		height: 48rpx;
		margin: 6rpx 0;
		/* background: #4CD964; */

		/* margin-right: 12rpx; */
	}

	.image1 {
		height: 90rpx;
		width: 90rpx;
		border-radius: 45rpx;
		/* background: #007AFF; */
		margin: 12rpx;
	}
</style>
