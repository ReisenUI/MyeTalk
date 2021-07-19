<template>
	<view class="view1">
		<view class="box1">
			<view class="nav">
				<view class="nav-itm" @tap="navpaint">
					<view><text class="iconfont iconhuahua1"></text></view>
					<text>画板</text>
				</view>

				<view class="nav-itm">
					<view @tap='gotojoke'>
						<!-- <text class="iconfont iconmind" @tap='gotojoke'></text> -->
						<image src="../../static/relief/joke.png" style="width: 110rpx;height: 110rpx;"></image>
					</view>
					<text>笑话</text>
				</view>
				<view class="nav-itm">
					<view> <text class="iconfont iconmind" @tap='gotojoke'></text></view>
					<text>深呼吸</text>
				</view>
			</view>
			<view class="nav">
				<view class="nav-itm" @tap="navgame2048">
					<view> <text class="iconfont iconairudiantubiaohuizhi-zhuanqu_youxi"></text></view>
					<text>2048</text>
				</view>
				<view class="nav-itm" @tap="navmusic">
					<view> <text class="iconfont iconyinyue"></text></view>
					<text>音乐</text>
				</view>
				<view class="nav-itm">
					<view> <text class="iconfont iconceshi" @tap='gototarot'></text></view>
					<text>测一测</text>
				</view>
			</view>
		</view>
		<view class="box2">

			<view class="introduce">
				<!-- <image src="https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1542204165-65bf26472b9b?ixid=MXwxMjA3fDB8MHxzZWFyY2h8M3x8bW92aWV8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80" class="img2" @click="navclick1">
				</image> -->
				<view class="intro1" @click="navclick1">
					电影推荐
				</view>

			</view>

			<view class="introduce">
				<!-- <image src="https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1544716278-ca5e3f4abd8c?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MXx8Ym9va3xlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80" class="img2" @click="navclick2">
				
			</image> -->
				<view class="intro2" @click="navclick2">
					书籍推荐
				</view>
			</view>
			<view class="introduce">
				<!-- <image src="https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1458560871784-56d23406c091?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Nnx8bXVzaWN8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80" class="img2" @click="navclick3">
				
			</image> -->
				<view class="intro3" @click="navclick3">
					音乐推荐
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {

			navclick1() {

				uni.navigateTo({

					url: "/pages/relief/movie"
				})
			},
			navclick2() {

				uni.navigateTo({

					url: "/pages/relief/book"
				})
			},
			navclick3() {

				uni.navigateTo({

					url: "/pages/relief/music"
				})
			},
			navpaint() {

				uni.navigateTo({

					url: "/pages/relief/paint"
				})
			},
			gototarot() {

				uni.navigateTo({

					url: "/pages/relief/tarot"
				})
			},
			gotojoke() {

				uni.navigateTo({

					url: "/pages/relief/joke"
				})
			},
			navmusic() {

				uni.navigateTo({

					url: "/pages/relief/playmusic"
				})
			},
			navgame2048() {

				uni.navigateTo({

					url: "/pages/relief/game2"
				})
			}
		}
	}
</script>

<style>
	@import url("../../common/relief/iconfont.css");

	.view1 {
		display: flex;
		flex-direction: column;
		align-items: center;
		/* width: 375px; */
		width: 100%;
	}

	.introduce {
		display: flex;
		align-items: center;
		flex-direction: column;
		box-sizing: border-box;
		width: 100%;




	}

	.img2 {
		border-radius: 60rpx;
		box-shadow: 4rpx 4rpx 10rpx #666666;
		width: 702rpx;
		align-items: center;
		text-align: center;
		margin: 24rpx;

	}

	.intro1 {
		display: flex;
		flex-direction: column;
		background: url("https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1542204165-65bf26472b9b?ixid=MXwxMjA3fDB8MHxzZWFyY2h8M3x8bW92aWV8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80") no-repeat;

		background-size: 100% auto;
		border-radius: 60rpx;
		box-shadow: 4rpx 4rpx 10rpx #666666;
		width: 94%;
		height: 400rpx;
		color: white;
		justify-content: center;
		text-align: center;
		font-size: 60rpx;
		margin: 10rpx 3%;

	}

	.intro2 {
		display: flex;
		flex-direction: column;
		background: url("https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1544716278-ca5e3f4abd8c?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MXx8Ym9va3xlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80") no-repeat;
		background-size: 100% auto;
		border-radius: 60rpx;
		box-shadow: 4rpx 4rpx 10rpx #666666;
		width: 94%;
		height: 400rpx;
		color: white;
		justify-content: center;
		text-align: center;
		font-size: 60rpx;
		margin: 10rpx 3%;

	}

	.intro3 {
		display: flex;
		flex-direction: column;
		background: url("https://rmt.dogedoge.com/fetch/~/source/unsplash/photo-1458560871784-56d23406c091?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Nnx8bXVzaWN8ZW58MHx8MHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=80") no-repeat;
		background-size: 100% auto;
		border-radius: 60rpx;
		box-shadow: 4rpx 4rpx 10rpx #666666;
		width: 94%;
		height: 400rpx;
		color: white;
		justify-content: center;
		text-align: center;
		font-size: 60rpx;
		margin: 10rpx 3%;

	}

	.box1 {
		/* display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 400rpx;
		background-color: #f8f8f8;
		position: fixed;
		top: --window-top; */
		/* css变量 */
		/* left: 0;
		top: 0;
		z-index: 99;
		text-align: center; */

	}

	.img1 {
		width: 100rpx;
		height: 100rpx;
	}

	.nav {
		display: flex;
		height: 200rpx;
		width: 100%;
		align-items: center;

	}

	.nav-itm {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 250rpx;
		/* margin-left: 30px;
		margin-right: 30px; */
		text-align: center;
		font-size: 32rpx;

	}

	.box2 {
		/* width: 375px; */
		overflow-y: scroll;
		width: 100%;
		/* margin-top: 400rpx; */
	}
    .icon1 {
    	height: 70rpx;
    	width: 70rpx;
    	margin-top: 10rpx;
    margin-right: 280rpx;
    }
	.title-nav{
		display: flex;
		flex-direction: row;
	}
	.title {
		
		display: flex;
		justify-content: center;
		align-items: center;
	}

	page {
		background-color: #f8f8f8;
		/* display: -webkit-box; */
		width: 100%;
		height: 100%;
	}
</style>
