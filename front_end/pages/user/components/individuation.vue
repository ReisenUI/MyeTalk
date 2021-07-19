<template>
<view>
	<view class='container'>
				<view class="view_line"></view>
				<view class='middle-item' hover-stay-time="150"@click="clickhandle()">
					<text class='text_2' >应用主题修改</text>
				</view>
	
				<view class="view_line"></view>
	
				<view class='middle-item' hover-class="opcity" hover-stay-time="150"@click="clickhandle()">
					<text class='text_2' >心情图标修改</text>
				</view>
	
				<view class="view_line"></view>
	
				<view class='middle-item' hover-class="opcity" hover-stay-time="150"@click="clickhandle()">
					<text class='text_2' >心情标签修改</text>
				</view>
	
				<view class="view_line"></view>
					
				<view class='middle-item' hover-class="opcity" hover-stay-time="150"v-on:click="clickhandle">
					<text class='text_2'>数据统计偏好</text>
				</view>
				<view class="view_line"></view>
				
			</view>

</view>
	
	
</template>

<script>
	export default{
		data(){
			return{
				
			}
		},
		methods:{
			clickhandle(){
				// console.log('此功能正在开发中，敬请等待')
				uni.showToast({
					title: '此功能正在开发中，敬请等待',
					icon: 'none',
					duration: 2000,
				})
			}
		}
	}
</script>

<style>
	.container {
			position: relative;
			top:30rpx;
			width: 96%;
			height: 450rpx;
			display: flex;
			flex-direction: column;
			border-radius: 15rpx;
			background-color: #FFFFFF;
			margin: 0rpx 2% 100rpx 2%;
		}
	
		.middle-item {
			height: 100%;
			display: flex;
			flex-direction: row;
			align-items: center;
		}
	.view_line {
			width: 90%;
			height: 10rpx;
			background: #dcdcdc;
			/* margin: 0rxp 5% 0rxp 5%; */
			margin: 0 auto;
			text-align: center;
		}
		.text_2{
			position: relative;
			left:35rpx;
			font-size: large;
		}
</style>
