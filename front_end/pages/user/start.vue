<template>
	<view class="card">
		<view :animation="animationData2" class="animation2">
		<image class="img1"src="../../static/snowman.png"style="flex-shrink: 0;display:flex;"@click="running2()"></image>
		</view>
		<view class="card1">
			<view>HI~</view>
			<view>欢迎来到心情说</view>
			<view>今天你有什么心事想向我传达吗</view>
		</view>
		<button class="but" v-on:click="toLogin">打开我的心情说</button>			
	</view>
				
</template>

<script>
	// TODO: 解决Warning
	// const animation = uni.requireNativePlugin('animation')
	var animation2 = uni.createAnimation({
	  transformOrigin: "50% 50%",
	  duration: 1000,
	  timingFunction: "ease",
	  delay: 0
	})
	export default {
		props: {
			item: {
				type: Object,
				default () {
					return {}
				}
			}
		},
		
		data() {
			return {
				animationData2: {},
			};
		},
		onUnload() {
			this.animationData2 = {}
		},
		onload(){
			this.animation2 = uni.createAnimation()  
		},
		methods: {
			toLogin() {
				this.animation2 = uni.createAnimation()
				this.animation2
					.translateY(20).step()
					.translateY(-20).step({duration:700})
				this.animationData2 = this.animation2.export()
				setTimeout(function() {
					console.log('打开我的心情说'),
						uni.navigateTo({
							url: "components/login"
						})
				    }, 1200);
			},
			toRegister(){
				uni.navigateTo({
					url: './components/register'
				});
			},	
			running2() {
				this.animation2 = uni.createAnimation()
				this.animation2
				    .translateY(20).step()
				    .translateY(-20).step({duration:700})
				this.animationData2 = this.animation2.export()
			}
		},
	}
</script>

<style lang="scss">	
	.card {
		// margin-top: 150rpx;
		display: flex;
		flex-direction: column;
		overflow: hidden;
	}
	.animation2{
		width: 100%;
		display: flex;
		align-items: center;
		.img1 {
			border-radius: 51px;
			width: 350rpx;
			height: 300rpx;
			margin: 100rpx auto 0rpx;
		}
	}
	

	.card1 view {
		white-space: nowrap;
		font-size: 1.2rem;
		font-weight: bold;
		text-align: center;
		margin: 50rpx 100rpx;
	}

	.but {
		margin-top: 120rpx;
		height: 100rpx;
		width: 90%;
		background-color: #000000;
		color: #FFFFFF;
		border-radius: 50rpx;
	}


</style>
