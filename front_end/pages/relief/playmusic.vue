<template>
	<view class="page">
	<view class="navbar">
		<view>
		<view style="margin-left: 100rpx;">聆听</view>
		<view style="margin-left: 250rpx;">世界的声音</view>
		</view>
	</view>
	<view class="background">
		<view  style="text-align: center;" v-for="(item,index) in current">
			<audio class="audiostyle" style="text-align: left" :src="item.src" :poster="item.poster" :name="item.name"
				:author="item.author" :action="audioAction" controls></audio>
		</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				current: [{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1590339359730-8444cd4664a0?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGFsaWNlfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: '致爱丽丝',
						author: '暂无',
						src: 'https://bjetxgzv.cdn.bspapp.com/VKCEYUGU-hello-uniapp/2cc220e0-c27a-11ea-9dfb-6da8e309e0d8.mp3',
					},
					{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1618580987827-8d73bdd196aa?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0fHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: 'All In Theme',
						author: '暂无',
						src: 'http://app.tiantai.com.cn/uploads/20200819/a25100936ec5d372c6805e5b476dbd59.mp3',
					},
					{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1618711103795-a20af977b6e9?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyfHx8ZW58MHx8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: 'ALONE',
						author: '暂无',
						src: 'http://app.tiantai.com.cn/uploads/20200819/7f88f2850ad50d8cc0ab1301579368ec.mp3',
					},
					{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1527661591475-527312dd65f5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fHN1bW1lcnxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: '菊次郎的夏天',
						author: '暂无',
						src: 'http://music.163.com/song/media/outer/url?id=443242.mp3',
					},
					{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1517309230475-6736d926b979?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8Y2hpbmF8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: 'CHINA-X',
						author: '暂无',
						src: 'http://music.163.com/song/media/outer/url?id=456175343.mp3',
					},
					{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1444962668425-360f59fa2c24?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8d2luZHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: '沉醉于风中',
						author: '暂无',
						src: 'http://music.163.com/song/media/outer/url?id=817880.mp3',
					
					},
					{
						poster: 'https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1464802686167-b939a6910659?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dW5pdmVyc2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80',
						name: '所念皆星河',
						author: '暂无',
						src: 'http://music.163.com/song/media/outer/url?id=1384026889.mp3',
					
					},
				],
				audioAction: {
					method: 'pause'
				}
			}
		}
	}
</script>
<style>
	.background {
		overflow-y: scroll;
		height: auto;
		width: 100%;
		padding-top:50rpx; 
		padding-top: 50rpx;
		display: flex;
		flex-direction: column;
		background: url("https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1576158129605-fb0c93eeedfc?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njd8fGdyYWR1YWwlMjBjaGFuZ2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80") ;
	}
	.audiostyle{
		margin: 10rpx 0;
	}
	
	.navbar{
		background: url("https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1419242902214-272b3f66ee7a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8dW5pdmVyc2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=80");
		background-size:contain;
		display: flex;
		flex-direction: column;
		justify-content: center;
		width: 100%;
		height: 300rpx;
		font-size: 70rpx;
		color: #ffffff;
		font-style: italic
	
		
		
	}
	page{
			background-color: #f8f8f8;
			
			width: 100%;
			height: 100%;
		}
</style>
