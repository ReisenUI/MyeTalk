<template>
	<view>
		<not-found info="没有草稿" v-if="!find"></not-found>
		<main-left-list-item :isShowGrey='false' :diary='item' v-for="(item, index) in draftList" :key='index'
			 v-else>
		</main-left-list-item>
	</view>
</template>

<script>
	import {
		Diary
	} from '../../../common/Diary.js'
	import {
		storageDraftList,
		getDraftList
	} from '../../../common/util/util.js'
	export default {
		data() {
			return {
				draftList: [],
				find: true
			}
		},
		methods: {},
		onLoad() {
			// uni.clearStorage();
			this.draftList = getDraftList().list;
			if(this.draftList == '' || this.draftList.length <= 0){
				this.find = false;
			}
		}
	}
</script>

<style>

</style>
