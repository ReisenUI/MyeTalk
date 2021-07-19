<template>
	<view>
		<view v-if="!find">
			<not-found info="没有收藏的日记"></not-found>
		</view>
		<main-left-list-item v-for="(item, index) in resDiary" :diary="item" :key="index" v-else/>
	</view>
</template>

<script>
	import {
		Diary
	} from '../../../common/Diary.js'
	import {
		getDiaryList
	} from '../../../common/util/util.js'
	export default {
		data() {
			return {
				resDiary: [],
				find: true
			}
		},
		methods: {
			
		},
		onLoad() {
			var diaryList = getDiaryList();
			var _this = this;
			diaryList.list.forEach(function(item) {
				if (item.isLike) {
					_this.resDiary.push(item);
				}
			});
			this.resDiary.push(new Diary());
			if (this.resDiary.length <= 0) {
				this.find = false;
			}
			console.log(this.resDiary);
		}
	}
</script>

<style>
	.itemList {
		padding: 40rpx 0;
		padding-left: 40rpx;
		display: flex;
		flex-direction: row;
	}

	.itemList #diaryData {
		padding-right: 30rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.itemList #diaryContentTitle view {
		padding: 5rpx 0;
		/* border: #007AFF solid; */
	}
</style>
