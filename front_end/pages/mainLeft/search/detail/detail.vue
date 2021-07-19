<template>
	<view>
		<view v-if="!find">
			<not-found :info="res"></not-found>
		</view>
		<view v-else>
			<!-- <main-left-list-item v-for="(item, index) in resDiary" :key="index" :day="item.date.day"
				:content="item.content" :title="item.title" :icon="item.mood[0].icon" /> -->
			<main-left-list-item v-for="(item, index) in resDiary" :diary="item" />
			<uni-load-more :status="loadMore.status" />
		</view>
	</view>
</template>

<script>
	import {
		getGetHead,
		getUrl,
		getDiaryListFromBack
	} from '../../../../common/util/util.js'
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import {
		Diary
	} from '../../../../common/Diary.js'
	export default {

		data() {
			return {
				resDiary: [],
				loadMore: {
					status: 'more'
				},
				msg: '', // 搜索的信息
				res: '没有找到您需要的日记',
				find: true,
				offset: 1,
				isDate: false
			}
		},
		methods: {
			getDiaryByKeyword(msg) {
				this.loadMore.status = 'loading';
				var _this = this;
				var url = '';
				var key = ''; // 键名不对
				var data = {
					pageNum: _this.offset++,
					pageSize: 10
				};
				if (this.isDate) {
					url = '/diary/calendar';
					data['selectedDate'] = msg;
				} else {
					url = '/diary/keyword';
					data['keyword'] = msg;
				}
				uni.request({
					url: getUrl(url),
					method: 'GET',
					header: getGetHead(),
					data: data,
					success: r => {
						console.log(r);
						let res = r.data.data;
						var list = getDiaryListFromBack(res.diaryList);
						_this.resDiary.push(...list);
						if (res.totalSize == 0) {
							_this.find = false;
							uni.hideLoading();
							return;
						}
						if (_this.resDiary.length >= res.totalSize) {
							_this.loadMore.status = 'noMore';
						} else {
							_this.loadMore.status = 'more';
						}
					},
					fail: (err) => {
						console.log('网络错误:', err);
					},
					complete: () => {}
				});
			}
		},
		computed: {

		},
		onLoad(e) {
			this.msg = e.msg
			if (e.isDate != undefined) {
				this.isDate = true;
			}
			console.log('搜索:', this.msg);
			this.getDiaryByKeyword(this.msg);
		},
		onReachBottom() {
			this.getDiaryByKeyword(this.msg)
			console.log("到底了");
		}
	}
</script>

<style>

</style>
