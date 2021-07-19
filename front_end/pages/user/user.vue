<template>
	<view>
		<view class="content">
			<!-- <view class="content" v-for="(item,index) in UserInfo" :key="index"> -->
			<view class="user-head">
				<view class="head-fixed">
					<navigator url="components/individuation">
						<uni-icons type="map" size="50" color="#333"></uni-icons>
					</navigator>
					<view class="head-title">我的</view>
					<navigator url="components/setting">
						<uni-icons type="gear" size="50" color="#333"></uni-icons>
					</navigator>
				</view>
				<view style="height: 100rpx;"></view>
			</view>

			<view class="user-box">

				<view class="user-card">
					<view class="inner-card1">
						<image :src="userInfo.avatar" mode="aspectFill"></image>
						<view class="inner-card-title">
							<view class="userName">
								{{userInfo.nickname}}
								<!-- ReisenU -->
							</view>
							<view class="userSetting" v-on:click="toModification">个人信息设置</view>
						</view>
						<view class="inner-card-personal" @click="openSelfPersonal(userInfo)">
							个人空间 >
						</view>
					</view>
					<view class="rj-card">
						<view class="rj-item-one">
							<view class="rj-num">
								{{userInfo.num_of_dairy}}
								<!-- 14 -->
							</view>
							<view class="num-info">日记数量</view>
						</view>
						<view class="rj-item-two">
							<view class="rj-num">
								{{userInfo.day_of_record}}
								<!-- 10 -->
							</view>
							<view class="num-info">记录天数</view>
						</view>
						<view class="rj-item">
							<view class="rj-num">
								{{userInfo.words_of_record}}
								<!-- 306 -->
							</view>
							<view class="num-info">记录文字</view>
							 
						</view>
					</view>
				</view>

				<view class="emotion-card1">
					<view class="inner-card2">
						<view class="emotion-head">心情指数</view>
						<uni-icons class="emotion-help" type="help" size="40" color="#555" v-on:click="emoToast">
						</uni-icons>
						<uni-icons type="paperplane" size="40" color="#555" v-on:click="emoRepost"></uni-icons>
					</view>
					<view class="inner-card3">
						<image src="/static/user/emotion.png" mode="aspectFill"></image>
						<view class="emo-num">{{userInfo.emotion_temper+5}}</view>
						<!-- <view class="emo-num">{{item.emotion_temper}}</view> -->
						<view class="inner-card4">
							<view class="emo-text1">相比上月：<text>增加 10%</text></view>
							<view class="emo-text2">要继续保持哟！</view>
						</view>
					</view>
				</view>

				<view class="emotion-card2">
					<view class="emotion-title">情绪变化</view>
					<!-- <view class="inner-card5">
					<view class="yzhou">
						<image class="emo-emo" src="../../static/happy2.png"></image>
						<image class="emo-emo" src="../../static/angry.png"></image>
						<image class="emo-emo" src="../../static/sad2.png"></image>
					</view>
					<view class="xzhou">
						<image class="emo-activity" src="../../static/love.png"></image>
						<image class="emo-activity" src="../../static/shopping.png"></image>
						<image class="emo-activity" src="../../static/house.png"></image>
						<image class="emo-activity" src="../../static/bag.png"></image>
						<image class="emo-activity" src="../../static/computer.png"></image>

					</view>
				</view> -->
				</view>

				<view class="recently-card">
					<view class="recently-title">最近的我</view>
					<view class="recently-info">
						<view class="line1">
							<view class="info1">{{mood1}}</view>
							<view class="info2">......</view>
						</view>
						<view class="line2">
							<view class="info3">{{activity1}}</view>
							<image :src="userInfo.avatar" mode="aspectFill"></image>
							<view class="info4">{{mood2}}</view>
						</view>
						<view class="line3">
							<view class="info5">{{activity1}}</view>
							<view class="info6">{{activity1}}</view>
						</view>
					</view>
				</view>

				<view class="book-card">
					<view class="book-title">
						<view class="titlename">故事书</view>
						<view class="book-text" v-on:click="bookAll">全部</view>
					</view>
					<view class="book-add">
						<uni-icons type="plusempty" size="100" color="#999"></uni-icons>
					</view>
				</view>


			</view>
		</view>
	</view>
</template>

<script>
	import {
		getDiaryList
	} from '../../common/util/util.js'

	export default {
		data() {
			return {
				userInfo: {},
				mood1: '',
				mood2: '',
				activity1: '',
				activity2: '',
				activity3: ''
			}
		},
		onLoad() {
			// console.log(getApp().globalData.token)
			// this.getMoodaActivity()
			var list = getDiaryList();
			try{
				this.activity1 = list.list[0].activities[0].activity;
				this.activity2 = list.list[1].activities[0].activity;
				this.activity3 = list.list[2].activities[0].activity;
				this.mood1 = list.list[0].mood[0].mood;
				this.mood2 = list.list[1].mood[0].mood;
			}catch(e){
				//TODO handle the exception
				this.activity1 = "学习";
				this.activity2 = "读书";
				this.activity3 = "睡觉";
				this.mood1 = "开心";
				this.mood2 = "平静";
			}
			console.log(this.list)
			console.log(uni.getStorageSync('token'))
			this.getUserInfo();
			uni.$on('ChooseImage', (res) => {
				this.userInfo.avatar = res;
			})
			uni.$on('saveChange', (res) => {
				this.userInfo.nickname = res;
			})
		},
		created() {
			this.getUserInfo()
		},
		methods: {

			getUserInfo() {
				const _this = this
				uni.request({
					url: 'https://zk123.top/mye/people/info',
					data: {},
					header: {
						Authorization: uni.getStorageSync('token')
					},
					method: 'GET',
					success: (res) => {
						// uni.showToast({
						// 	title: '资料请求成功',
						// });
						_this.userInfo = res.data.data;
						console.log(_this.userInfo)

						uni.setStorage({
							key: 'nickname',
							data: _this.userInfo.nickname,
							success: function(e) {
								// console.log('123',uni.getStorageSync('nickname'),_this.userInfo.nickname);
								console.log("存储昵称到本地成功！");
							}
						})
					},
					fail: (err) => {
						uni.showToast({
							icon: 'loading',
							title: '请求失败'
						})
						setTimeout(function() {
							uni.reLaunch({
								url: 'components/login'
							})
						}, 1200);
					}
				})

				// uni.getStorage({
				// 	key:'userInfo',
				// 	success:res=>{
				// 		this.userInfo=res.data
				// 	},
				// 	fail:()=>{
				// 		this.userInfo={}
				// 	}
				// })
			},
			clickhandle() {
				console.log('点击我了')
			},
			openSelfPersonal(userInfo) {
				console.log(userInfo);
				const params = {
					userId: userInfo.id,
					nickname: userInfo.nickname,
				}
				uni.navigateTo({
					url: "../community-personalCenter/community-personalCenter?params="+JSON.stringify(params)
				})
			},
			toSetting() {
				console.log('点击设置'),
					uni.navigateTo({
						url: "./components/setting"
					})
			},
			toIndividuation() {
				console.log('点击个性化'),
					uni.navigateTo({
						url: "components/individuation"
					})
			},
			toModification() {
				console.log('点击个人信息'),
					uni.navigateTo({
						url: "components/modification"
					})
			},
			emoToast() {
				console.log('点击心情指数'),
					uni.showToast({
						title: '心情指数是指一段时间内心情的评分',
						icon: 'none',
						duration: 2000,
					})
			},
			emoRepost() {
				console.log('点击转发'),
					uni.showToast({
						title: '转发功能还未完成，敬请期待！',
						icon: 'none',
						duration: 2000
					})
			},
			bookAll() {
				console.log('全部功能'),
					uni.showToast({
						title: '全部功能还未完成，敬请期待！',
						icon: 'none',
						duration: 2000
					})
			},
		},


	}
</script>

<style lang="scss">
	.user-head .head-fixed {
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 100%;
		height: 100rpx;
		background-color: #FFFFFF;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 99;
		text-align: center;
		box-sizing: border-box;
		padding: 0 30rpx;

		.head-title {
			font-size: 38rpx;
		}
	}

	.user-box {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		margin: 30rpx;

		.user-card {
			display: flex;
			flex-direction: column;
			border-radius: 10rpx;
			box-shadow: 0 0 10rpx 2rpx rgba($color: #333, $alpha: 0.1);
			box-sizing: border-box;
			background: #FFFFFF;
			margin-bottom: 30rpx;
			padding: 20rpx;

			.inner-card1 {
				display: flex;
				flex-direction: row;
				padding: 10rpx;
				justify-content: space-between;
				align-items: center;
				position: relative;

				image {
					width: 80rpx;
					height: 80rpx;
					border-radius: 50%;
				}

				.inner-card-title {
					display: flex;
					flex-direction: column;
					position: absolute;
					left: 110rpx;
					line-height: 1.5;

					.userName {
						font-size: 32rpx;
					}

					.userSetting {
						font-size: 26rpx;
						color: #777;
					}
				}

				.inner-card-personal {
					font-size: 30rpx;
					color: #777;
				}
			}

			.rj-card {
				display: flex;
				justify-content: space-around;
				box-sizing: border-box;
				padding: 30rpx 0;
				align-items: center;
				text-align: center;
				line-height: 1.6;

				.rj-item,
				.rj-item-one,
				.rj-item-two {
					font-size: 30rpx;
					font-weight: 400;
					width: 33.33%;

					.num-info {
						font-size: 28rpx;
						color: #666;
					}
				}

				.rj-item-one,
				.rj-item-two {
					border-right: 1px solid #f3f3f3;
				}

			}
		}

		.emotion-card1 {
			display: flex;
			flex-direction: column;
			border-radius: 10rpx;
			box-shadow: 0 0 10rpx 2rpx rgba($color: #333, $alpha: 0.1);
			box-sizing: border-box;
			background: #FFFFFF;
			margin-bottom: 30rpx;
			padding: 20rpx;

			.inner-card2 {
				display: flex;
				flex-direction: row;
				padding: 10rpx;
				justify-content: space-between;
				align-items: center;
				position: relative;

				.emotion-head {
					font-size: 32rpx;
					font-weight: 450;
				}

				.emotion-help {
					position: absolute;
					left: 140rpx;
				}
			}

			.inner-card3 {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;
				box-sizing: border-box;
				padding: 20rpx 50rpx;
				position: relative;

				image {
					width: 100rpx;
					height: 200rpx;
				}

				.emo-num {
					position: absolute;
					left: 170rpx;
					font-size: 34rpx;
					font-weight: 450;
				}

				.inner-card4 {
					line-height: 2;
					text-align: center;

					.emo-text1 {
						color: #666;
						font-size: 32rpx;

						text {
							color: #000;
						}
					}

					.emo-text2 {
						color: #666;
						font-size: 28rpx;
					}
				}
			}
		}

		.emotion-card2 {
			display: flex;
			flex-direction: column;
			border-radius: 10rpx;
			box-shadow: 0 0 10rpx 2rpx rgba($color: #333, $alpha: 0.1);
			box-sizing: border-box;
			background: #FFFFFF;
			margin-bottom: 30rpx;
			padding: 20rpx;

			.emotion-title {
				padding: 10rpx;
				font-size: 32rpx;
				font-weight: 450;
			}
		}

		.recently-card {
			display: flex;
			flex-direction: column;
			border-radius: 10rpx;
			box-shadow: 0 0 10rpx 2rpx rgba($color: #333, $alpha: 0.1);
			box-sizing: border-box;
			background: #FFFFFF;
			margin-bottom: 30rpx;
			padding: 20rpx;

			.recently-title {
				padding: 10rpx;
				font-size: 32rpx;
				font-weight: 450;
			}

			.recently-info {
				display: flex;
				flex-direction: column;
				box-sizing: border-box;
				flex-wrap: wrap;
				color: #555;

				.line1,
				.line2,
				.line3 {
					display: flex;
					flex-direction: row;
					justify-content: space-between;
					align-items: center;
					position: relative;

					image {
						// border: 1px solid #aaa;
						width: 150rpx;
						height: 150rpx;
						border-radius: 50%;
					}

					.info1,
					.info2,
					.info3,
					.info4,
					.info5,
					.info6 {
						border: 1px solid #aaa;
						width: 100rpx;
						height: 100rpx;
						display: flex;
						align-items: center;
						justify-content: center;
						border-radius: 50%;
						margin: 10rpx;
					}

					.info1 {
						width: 230rpx;
						height: 230rpx;
						margin-right: 80rpx;
					}

					.info2 {
						width: 100rpx;
						height: 100rpx;
						position: absolute;
						top: 160rpx;
						right: 100rpx;
					}

					.info3 {
						width: 210rpx;
						height: 210rpx;
					}

					.info4 {
						width: 130rpx;
						height: 130rpx;
					}

					.info5 {
						width: 190rpx;
						height: 190rpx;
						margin-right: 100rpx;

					}

					.info6 {
						width: 160rpx;
						height: 160rpx;
						position: absolute;
						bottom: 50rpx;
						right: 70rpx;
					}
				}

				.line1,
				.line3 {
					justify-content: center;
				}

			}
		}

		.book-card {
			display: flex;
			flex-direction: column;
			border-radius: 10rpx;
			box-shadow: 0 0 10rpx 2rpx rgba($color: #333, $alpha: 0.1);
			box-sizing: border-box;
			background: #FFFFFF;
			padding: 20rpx;

			.book-title {
				display: flex;
				flex-direction: row;
				padding: 10rpx;
				justify-content: space-between;
				align-items: center;

				.titlename {
					font-size: 32rpx;
					font-weight: 450;
				}
			}

			.book-add {
				display: flex;
				justify-content: center;
				align-items: center;
				width: 200rpx;
				height: 200rpx;
				margin: 10rpx 230rpx;
				border: 1px solid #eee;
				border-radius: 10rpx;
			}
		}
	}
</style>
