<template>
	<view>
		
		<view class="likeMessage" v-for="(item,index) in likeList" :key="index" @click="openArticle(item)">
			<view class="likePerson" @click.stop="openchatPerson(item)">
				<view class="likePerson-photo">
					<image src="/static/head_photo/1.jpg" mode="aspectFit"></image>
				</view>
				<view class="likePerson-info">
					<view class="name">点赞者的昵称</view>
					<view class="time">{{commentTime[index]}}</view>
				</view>
			</view>
			<view class="like">
				赞了这个故事
			</view>
			<view class="communityPassage">
				<view class="passageAuthor">@{{item.nickname}}</view>
				<view class="passageTitle">#发帖的标题#</view>
				<view class="passageContent">
					发帖的内容
				</view>
			</view>
		</view>
		
		<view class="likeComment">
			<view class="likePerson">
				<view class="likePerson-photo">
					<image src="/static/head_photo/zhang.jpeg" mode="aspectFit"></image>
				</view>
				<view class="likePerson-info">
					<view class="name">评论点赞者昵称</view>
					<view class="time">时间</view>
				</view>
			</view>
			<view class="like">
				赞了这个评论
			</view>
			<view class="content">
				<view class="comment-content">
					<view class="commentAuthor">@文章作者呢称：</view>
					<view class="commentContent">评论的内容</view>
				</view>
				<view class="communityPassage">
					<view class="passageAuthor">@文章作者呢称</view>
					<view class="passageTitle">#发帖的标题#</view>
					<view class="passageContent">
						发帖的内容
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				messageInfo: [],
				likeList:[],
				commentTime:[],
			}
		},
		onLoad() {
			this.getLikeMessage();
		},
		methods: {
			getLikeMessage() {
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
						console.log(_this.messageInfo);
						for(var i=0; i<_this.messageInfo.length; i++){
							_this.commentTime.push(_this.messageInfo[i].time);
							_this.commentTime[i] = _this.commentTime[i].replace(
								/(\d+)\s*-\s*(\d+)-\s*(\d+)-\s*(\d+)-\s*(\d+)-\s*(\d+)/, "$1-$2-$3 $4:$5:$6");
							if(_this.messageInfo[i].type === '点赞'){
								_this.likeList.push(_this.messageInfo[i]);
							}
						}
					}
				})
			},
			openArticle(item){
				var articleId = item.articleId;
				uni.navigateTo({
					url: "../../../pages/community-passage/passage-lingxian/passage-lingxian" +
						"?articleId=" +
						articleId
				})
			},
			openchatPerson(item){
				var userId = item.userId;
				console.log(userId);
				// uni.navigateTo({
				// 	url: "../../../pages/community-passage/passage-lingxian/passage-lingxian" +
				// 		"?articleId=" +
				// 		articleId
				// })
			},
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #eeeeee;
	}
	.likeMessage {
		background: #FFFFFF;
		display: flex;
		flex-direction: column;
		padding: 10rpx 40rpx;
		margin: 16rpx 0;
		padding-bottom: 20rpx;
		.likePerson {
			display: flex;
			height: 100rpx;
			align-items: center;
			.likePerson-photo image {
				margin-right: 10rpx;
				width: 60rpx;
				height: 60rpx;
				border-radius: 50%;
			}
			.likePerson-info {
				margin: 10rpx;
				line-height: 1.6;
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
		.like{
			margin-bottom: 10rpx;
			padding-left: 16rpx;
			color: #555;
		}
		.communityPassage {
			box-sizing: border-box;
			font-size: 30rpx;
			padding: 16rpx;
			line-height: 1.7;
			color: #777;
			background: #f7f7f7;
			border-radius: 14rpx;
			.passageAuthor {
				color: #000;
			}
		}
	}
	.likeComment{
		background: #FFFFFF;
		margin: 16rpx 0;
		.likePerson {
			display: flex;
			height: 100rpx;
			padding: 10rpx 40rpx;
			align-items: center;
			.likePerson-photo image {
				margin: 10rpx 0;
				margin-right: 10rpx;
				width: 60rpx;
				height: 60rpx;
				border-radius: 50%;
			}
			.likePerson-info {
				margin: 10rpx;
				line-height: 1.6;
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
		.like{
			margin-bottom: 10rpx;
			padding-left: 56rpx;
			color: #555;
		}
		.content{
			background: #f7f7f7;
			padding: 20rpx 30rpx;
			box-sizing: border-box;
			.comment-content{
				display: flex;
				margin-bottom: 16rpx;
				line-height: 1.7;
				.commentContent{
					color: #555;
				}
			}
			.communityPassage{
				background-color: #fefefe;
				border-radius: 14rpx;
				box-sizing: border-box;
				font-size: 30rpx;
				padding: 16rpx;
				line-height: 1.7;
				color: #555;
				.passageAuthor{
					color: #000;
				}
			}
		}
	}
</style>
