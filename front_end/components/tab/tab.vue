<template>
	<view>

		<view v-if="mode === 'tab_one'" class="tab">
			<view class="hotTopic">
				热门话题
			</view>
			<scroll-view class="tab-scroll" scroll-x>
				<view class="tab-scroll-box">
					<view class="tab-scroll-item" :class="{active:activeIndex === index}" @click="clickTab(item, index)"
						 v-for="(item, index) in tabList" :key="index">
						# {{item.topic_name}} #
					</view>
				</view>
			</scroll-view>
			<view class="tab-bottom">
				<view class="update">最近更新</view>
				<view class="another">另一个我</view>
				<view class="selected">只看精选</view>
			</view>
		</view>

		<view v-if="mode === 'tab_two'" class="tab mode_tab_ajmd">

			<navigator url="../../pages/community-passage/passage-ajimide/passage-ajimide">
				<view class="tab_ajmd_top">
					<view class="top_zhiding">置顶</view>
					<view class="top_content">阿基米德专区介绍和发帖须知</view>
				</view>
			</navigator>

			<view class="hotTopic">
				热门话题
			</view>

			<scroll-view class="tab-scroll" scroll-x>
				<view class="tab-scroll-box">
					<!-- 父组件向子组件传递数据 -->
					<view class="tab-scroll-item" :class="{active:activeIndex === index}" @click="clickTab(item, index)"
						v-for="(item, index) in tabList" :key="index">
						<!-- # {{item.article.tag}} # -->
						# {{item.topic_name}} #
					</view>
				</view>
			</scroll-view>
			<view class="tab-bottom">
				<view class="update">最近更新</view>
				<view class="selected">只看精选</view>
			</view>
		</view>


	</view>

</template>

<script>
	export default {
		name: "tab",
		props: {
			mode: {
				type: String,
				default: 'base'
			},
			// 父组件向子组件传递数据
			/* receiveTabList: {
				// 数据类型是数组
				type: Array,
				// 返回一个空数组
				default () {
					return []
				}
			} */
		},
		data() {
			return {
				baseUrl: 'https://zk123.top/mye',
				activeIndex: 0,
				tabList: [],
				heatList: [],
			};
		},
		created() {
			this.getTabList();
			this.getArticleList();
		},
		methods: {
			clickTab(item, index) {
				console.log(item, index);
				this.activeIndex = index;
				console.log(item.topic_id);
				this.$emit('tab', {
					data: item,
					topic_id: item.topic_id
				})
			},
			getTabList() {
				var _self = this;
				uni.request({
					url: this.baseUrl + '/c/topic', //接口地址
					// header: {
					// 'content-type': 'application/x-www-form-urlencoded',  //自定义请求头信息
					// 'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiLlv4Pmg4Xor7QiLCJqdGkiOiIxMDAwNCIsImV4cCI6MTYxODM1OTk2MSwiaWF0IjoxNjE4MzE2NzYxLCJyb2xlIjoidXNlciIsInBlcm1pc3Npb24iOiJ1c2VyIn0.V3h2SGQCQ6gV9m50v4UwN8VmPnlclc2upP6OoijFKrquYvsGSnv_Mda7EOAZ6PUNWqPoT-unyaYABmjwsWizug'
					// },
					data: {
						region: 0,
						limit: 5,
						offset: 2
					},
					success: (res) => {
						// 请求成功之后将文章列表数据赋值给tabList
						_self.tabList = res.data.data.ratingList; //根据API数据找到对应的集合
						// console.log(_self.tabList);
						/* let obj = res.data.data.ratingList;
						for (let i = 0; i < obj.length; i++) {
							// console.log(_self.tabList[i].heat);
							_self.heatList = _self.tabList.sort(function(a,b){
								a = _self.tabList[i].heat;
								b = _self.tabList[i].heat;
								return b-a;
							})
							console.log(_self.heatList[i].heat);
						}; */
						// console.log(_self.heatList);
					}
				});
			},
		}
	}
</script>

<style lang="scss">
	.tab {
		width: 100%;
		box-sizing: border-box;
		.hotTopic {
			font-size: 32rpx;
			color: #000;
			margin-top: 16rpx;
			margin-left: 20rpx;
		}
		.tab-scroll {
			display: flex;
			box-sizing: border-box;
			margin-right: 20rpx;
			.tab-scroll-box {
				display: flex;
				align-items: center;
				height: 35px;
				text-align: center;
				flex-wrap: nowrap;
				box-sizing: border-box;
				margin: 10rpx;
				margin-right: 20rpx;
				.tab-scroll-item {
					flex-shrink: 0;
					padding: 6rpx 20rpx;
					border: 1px solid #C0C0C0;
					box-shadow: 0 0 10rpx 2rpx rgba($color: #000000, $alpha: 0.1);
					border-radius: 28rpx;
					margin: 10rpx 20rpx;
					font-size: 28rpx;
					box-sizing: border-box;
					color: #666666;
					
					&.active{
						background: #ccd8f2;
					}
				}
			}
		}
		.tab-bottom {
			display: flex;
			text-align: center;
			align-items: center;
			width: 100%;
			justify-content: space-between;
			font-size: 28rpx;
			color: #aaaaaa;
			border-bottom: 1px solid #C0C0C0;
			box-sizing: border-box;
			padding: 10rpx 20rpx;
			
			&.active{
				color: #444444;
			}
		}
	}

	.tab .tab-bottom .update:hover,
	.another:hover,
	.selected:hover {
		color: #444444;
	}

	.tab_ajmd_top {
		width: 97%;
		height: 60rpx;
		background: #FFFFFF;
		display: flex;
		margin: 10rpx;
		font-size: 28rpx;
		color: #555555;
		box-sizing: border-box;
		padding: 30rpx 0;
		text-align: center;
		align-items: center;
		border-radius: 8rpx;
		box-shadow: 0 0 10rpx 2rpx rgba($color: #000000, $alpha: 0.1);
		
		.top_zhiding {
			margin-left: 30rpx;
			margin-right: 30rpx;
			border-radius: 10rpx;
			padding: 4rpx;
			background: #d0deee;
		}
	}
</style>
