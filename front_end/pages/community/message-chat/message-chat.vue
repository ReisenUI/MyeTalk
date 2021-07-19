<template>
	<view>
		<view v-if="messageInfo.length>0">
			<view class="chatMessage" v-for="(item,index) in messageInfo" :key="index" @click="openArticle(item)">
				<view class="chatPerson" @click.stop="openchatPerson(item)">
					<view class="chatPerson-photo">
						<image :src="item.p1_avatar" mode="aspectFit"></image>
					</view>
					<view class="chatPerson-info">
						<view class="name">{{item.p1_nickname}}</view>
						<view class="time">{{commentTime[index]}}</view>
					</view>
				</view>
				
				<view class="chatContent">
					{{item.p1_content}}
				</view>
				
				<view class="communityPassage">
					<view class="passageAuthor">@{{item.p1_nickname}}</view>
					<view class="passageTitle">#{{item.p2_title}}#</view>
					<view class="passageContent">
						{{item.p2_content}}
					</view>
				</view>
			</view>
		</view>
		<view class="comment-null" v-else>
			还没有评论哦~
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				messageInfo: [],
				chatList:[],
				commentTime:[],
			}
		},
		onLoad() {
			this.getChatMessage();
		},
		methods: {
			getChatMessage() {
				const _this = this;
				uni.request({
					url: 'https://zk123.top/mye/c/message',
					data: {},
					header: {
						Authorization: uni.getStorageSync('token')
					},
					method: 'GET',
					success: (res) => {
						_this.messageInfo = res.data.data;
						console.log('messageInfo', _this.messageInfo);
						 for(var i=0; i<_this.messageInfo.length; i++){
							console.log('p1_id:',_this.messageInfo[i].p1_id,'p2_id:',_this.messageInfo[i].p2_id);
						 } 
						for(var i=0; i<_this.messageInfo.length; i++){
							_this.commentTime.push(_this.messageInfo[i].time);
							_this.commentTime[i] = _this.commentTime[i].replace(
								/(\d+)\s*-\s*(\d+)-\s*(\d+)-\s*(\d+)-\s*(\d+)-\s*(\d+)/, "$1-$2-$3 $4:$5:$6");
						}
					}
				})
			},
			openArticle(item){
				var articleId = item.p2_articleId;
				uni.navigateTo({
					url: "../../../pages/community-passage/passage-lingxian/passage-lingxian" +
						"?articleId=" +
						articleId
				})
			},
			openchatPerson(item){
				console.log(item);
				const params = {
					userId: item.p1_id,
					nickname: item.p1_nickname,
				}
				uni.navigateTo({
					url: "../../community-personalCenter/community-personalCenter?params="+JSON.stringify(params)
				})
			},
		}
	}
</script>

<style lang="scss">
	.comment-null{
		display: flex;
		flex-direction: column;
		height: 200rpx;
		align-items: center;
		justify-content: center;
		color: #666;
		font-size: 32rpx;
	},
	.chatMessage {
		background: #FFFFFF;
		display: flex;
		flex-direction: column;
		padding: 30rpx 40rpx;
		margin: 16rpx 0;
		border-bottom: 1px solid #f5f5f5;
		.chatPerson {
			display: flex;
			height: 100rpx;
			padding: 4rpx 0;
			align-items: center;
			.chatPerson-photo image {
				margin: 10rpx 0;
				margin-right: 10rpx;
				width: 60rpx;
				height: 60rpx;
				border-radius: 50%;
			}
			.chatPerson-info {
				margin: 10rpx;
				.name {
					font-size: 30rpx;
					color: #000;
				}
				.time {
					font-size: 24rpx;
					color: #999;
				}
			}
		}
		.chatContent {
			display: flex;
			float: left;
			font-size: 30rpx;
			line-height: 1.6;
			color: #555;
			margin: 10rpx 0;
		}
		.communityPassage {
			box-sizing: border-box;
			font-size: 30rpx;
			padding: 16rpx;
			line-height: 1.7;
			color: #777;
			background: #F5F5F5;
			border-radius: 14rpx;
			.passageAuthor {
				color: #000;
			}
		}
	}
</style>
