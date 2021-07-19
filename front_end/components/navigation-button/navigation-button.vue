<template>
	<view>
		<button @tap="onTap"></button>
		<mx-datepicker :show="showPicker" @confirm="onSelected" @cancel="onCancel" :value="start" type="date" color="#09181f">
		</mx-datepicker>
	</view>
</template>

<script>
	export default {
		name: "navigation-button",
		props: {
			startDay: {
				type: Object,
				default: function() {
					return {
						year: 2021,
						month: 12,
						day: 10
					}
				}
			}
		},
		data() {
			return {
				showPicker: false,
			};
		},
		methods: {
			onTap() {
				this.showPicker = !this.showPicker;
			},
			onSelected(e) {
				this.showPicker = false;
				let arr = e.value.split('/');
				let resDate = {
					year: Number(arr[0]),
					month: Number(arr[1]),
					day: Number(arr[2])
				}
				this.$emit('selectDate', resDate);
			},
			onCancel() {
				this.showPicker = false;
			}
		},
		computed:{
			start(){
				let date = this.startDay;
				return date.year + '/' + date.month + '/' + date.day;
			}
		},
		mounted() {
			
		}
	}
</script>

<style>
	button {
		background-color: transparent;
		border-radius: 0rpx;
		top: 0;
		left: 0;
		right: 0;
		width: 40%;
		height: 80rpx;
		position: fixed;
		z-index: 1000;
	}

	button::after {
		border: none;
	}
</style>
