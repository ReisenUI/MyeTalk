<template>
	<view class="calendar-wrapper">
		<!-- 日历的head -->
		<transition name="year-panel" enter-active-class="year-panel-enter" leave-active-class="year-panel-leave">
			<view class="year-view" v-show="!year_month" style="transform: translateY(0rpx) 0.5s;">
				<view class="header" v-if="headerBar">
					<view class="pre" @click="changeYear('pre')">-</view>
					<view class="header-ym">{{y+'年'}}</view>
					<view class="next" @click="changeYear('next')">-</view>
				</view>
				<view class="test-block" :style="{ height: 560 + 'rpx' }">
					<view class="month-card" v-for="index in months" :id="'months_'+index" :key="index+1"
						:class="isSelectedMonth(index)" @click="changeMonthView">
						<view class="inner-text" :id="'months_'+index">
							{{index + 1 + "月"}}
						</view>
					</view>
				</view>
			</view>
		</transition>
		<transition name="month-panel">
			<view class="month-view" v-show="year_month">
				<view class="header" v-if="headerBar">
					<view class="header-ym">{{y + '年'}}</view>
					<view class="header-ym" @click="changeMonthView">{{formatNum(m) + '月'}}</view>
				</view>
				<view class="week">
					<view class="week-day" v-for="(item, index) in weekDay" :key="index">{{ item }}</view>
				</view>
				<view :class="{ hide: !monthOpen }" class="content" :style="{ height: height }">
					<view :style="{ top: positionTop + 'rpx' }" class="days">
						<view class="item" v-for="(item, index) in dates" :key="index">
							<!-- @click="selectOne(item, $event)" -->
							<view class="day" :class="{
				                    choose: choose == `${item.year}-${item.month}-${item.date}`&&item.isCurM,
				                    nolm: !item.isCurM,
				                    today: isToday(item.year, item.month, item.date),
				                    isWorkDay: isWorkDay(item.year, item.month, item.date),
									recordedDay: item.recorded
				                }" @click="item.recorded === true?getDiaryByDate(item):''">
								{{ Number(item.date) }}
							</view>
							<view class="markDay" v-if="isMarkDay(item.year, item.month, item.date)&&item.isCurM">
							</view>
						</view>
					</view>
				</view>
			</view>
		</transition>
	</view>
</template>

<script>
	export default {
		name: 'ren-calendar',
		props: {
			// 星期几为第一天(0为星期日)
			weekstart: {
				type: Number,
				default: 0
			},
			// 标记的日期
			markDays: {
				type: Array,
				default: () => {
					return [];
				}
			},
			//是否展示月份切换按钮
			headerBar: {
				type: Boolean,
				default: true
			},
			// 是否展开
			open: {
				type: Boolean,
				default: true
			},
			//是否可收缩
			collapsible: {
				type: Boolean,
				default: true
			},
			//未来日期是否不可点击
			disabledAfter: {
				type: Boolean,
				default: true
			}
		},
		data() {
			return {
				weektext: ['日', '一', '二', '三', '四', '五', '六'],
				y: new Date().getFullYear(), // 年
				m: new Date().getMonth() + 1, // 月
				dates: [], // 当前月的日期数据
				positionTop: 0,
				monthOpen: true,
				choose: '',
				year_month: false,
				selectedMonth: '',
				baseUrl: 'https://zk123.top/mye',
				recordedDate: [],
			};
		},
		created() {
			this.dates = this.monthDay(this.y, this.m);
			this.selectedMonth = this.y + "_" + this.m;
			!this.open && this.toggle();
		},
		mounted() {
			this.choose = this.getToday().date;
		},
		computed: {
			// 顶部星期栏
			weekDay() {
				return this.weektext.slice(this.weekstart).concat(this.weektext.slice(0, this.weekstart));
			},
			height() {
				return (this.dates.length / 7) * 80 + 'rpx';
			},
			months() {
				let mon = [];
				for (let i = 0; i < 12; ++i) mon.push(i);
				return mon;
			}
		},
		methods: {
			getDiaryByDate(i) {
				let date = `${i.year}-${i.month}-${i.date}`;
				console.log(date);
				uni.navigateTo({
					url: '../../pages/mainLeft/search/detail/detail?msg=' + date + '&isDate=' + 'true'
				})
			},
			formatNum(num) { // 月和日规格化
				let res = Number(num);
				return res < 10 ? '0' + res : res;
			},
			getToday() {
				let date = new Date();
				let y = date.getFullYear();
				let m = date.getMonth();
				let d = date.getDate();
				let week = new Date().getDay();
				let weekText = ['日', '一', '二', '三', '四', '五', '六'];
				let formatWeek = '星期' + weekText[week];
				let today = {
					date: y + '-' + this.formatNum(m + 1) + '-' + this.formatNum(d),
					week: formatWeek
				};
				return today;
			},
			// 获取当前月份数据
			monthDay(y, month) {
				/**
				 * 此处需要获得哪天写过日记，然后渲染成有日记的class！！！！！！
				 */
				console.log(y, month);
				const _this = this;
				let currentM = y + "-" + month;
				let recordDates = [];
				let dates = [];
				let m = Number(month);
				let firstDayOfMonth = new Date(y, m - 1, 1).getDay(); // 当月第一天星期几
				let lastDateOfMonth = new Date(y, m, 0).getDate(); // 当月最后一天
				let lastDayOfLastMonth = new Date(y, m - 2, 0).getDate(); // 上一月的最后一天
				let weekstart = this.weekstart == 7 ? 0 : this.weekstart;
				let startDay = (() => {
					// 周初有几天是上个月的
					if (firstDayOfMonth == weekstart) {
						return 0;
					} else if (firstDayOfMonth > weekstart) {
						return firstDayOfMonth - weekstart;
					} else {
						return 7 - weekstart + firstDayOfMonth;
					}
				})();
				let endDay = 7 - ((startDay + lastDateOfMonth) % 7); // 结束还有几天是下个月的

				uni.request({
					url: _this.baseUrl + '/calendar',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						selectedDate: currentM
					},
					success: (e) => {
						let date = e.data.data;
						recordDates = Object.keys(date);
						for (let i = 1; i <= startDay; i++) {
							dates.push({
								date: _this.formatNum(lastDayOfLastMonth - startDay + i),
								day: weekstart + i - 1 || 7,
								month: m - 1 >= 0 ? _this.formatNum(m - 1) : 12,
								year: m - 1 >= 0 ? y : y - 1
							});
						}
						for (let j = 1; j <= lastDateOfMonth; j++) {
							dates.push({
								// date: _this.formatNum(j),
								date: j,
								day: (j % 7) + firstDayOfMonth - 1 || 7,
								// month: _this.formatNum(m),
								month: m,
								year: y,
								isCurM: true, //是否当前月份
								recorded: recordDates.indexOf("" + j) != -1
							});
						}
						for (let k = 1; k <= endDay; k++) {
							dates.push({
								date: _this.formatNum(k),
								day: (lastDateOfMonth + startDay + weekstart + k - 1) % 7 || 7,
								month: m + 1 <= 11 ? this.formatNum(m + 1) : 0,
								year: m + 1 <= 11 ? y : y + 1
							});
						}
					}
				})

				return dates;
			},
			isWorkDay(y, m, d) {
				//是否工作日
				let ymd = `${y}/${m}/${d}`;
				let formatDY = new Date(ymd.replace(/-/g, '/'));
				let week = formatDY.getDay();
				if (week == 0 || week == 6) {
					return false;
				} else {
					return true;
				}
			},
			isFutureDay(y, m, d) {
				//是否未来日期
				let ymd = `${y}/${m}/${d}`;
				let formatDY = new Date(ymd.replace(/-/g, '/'));
				let showTime = formatDY.getTime();
				let curTime = new Date().getTime();
				if (showTime > curTime) {
					return true;
				} else {
					return false;
				}
			},
			// 标记日期
			isMarkDay(y, m, d) {
				let flag = false;
				for (let i = 0; i < this.markDays.length; i++) {
					let dy = `${y}-${m}-${d}`;
					if (this.markDays[i] == dy) {
						flag = true;
						break;
					}
				}
				return flag;
			},
			isToday(y, m, d) {
				let checkD = y + '-' + m + '-' + d;
				let today = this.getToday().date;
				if (checkD == today) {
					return true;
				} else {
					return false;
				}
			},
			isSelectedMonth(curM) {
				let curMonth = false;
				let date = new Date();
				let y = this.y;
				let t = this.selectedMonth.split('_');
				if (y == t[0] && curM == t[1] - 1) {
					curMonth = true;
				}
				return {
					selectedMonth: curMonth
				};
			},
			// 展开收起
			toggle() {
				this.monthOpen = !this.monthOpen;
				if (this.monthOpen) {
					this.positionTop = 0;
				} else {
					let index = -1;
					this.dates.forEach((i, x) => {
						this.isToday(i.year, i.month, i.date) && (index = x);
					});
					this.positionTop = -((Math.ceil((index + 1) / 7) || 1) - 1) * 80;
				}
			},
			// 点击回调
			selectOne(i, event) {
				let date = `${i.year}-${i.month}-${i.date}`;
				let selectD = new Date(date).getTime();
				let curTime = new Date().getTime();
				let week = new Date(date).getDay();
				let weekText = ['日', '一', '二', '三', '四', '五', '六'];
				let formatWeek = '星期' + weekText[week];
				let response = {
					date: date,
					week: formatWeek
				};
				if (!i.isCurM) {
					// console.log('不在当前月范围内');
					return false;
				}
				if (selectD > curTime) {
					if (this.disabledAfter) {
						console.log('未来日期不可选');
						return false;
					} else {
						this.choose = date;
						this.$emit('onDayClick', response);
					}
				} else {
					this.choose = date;
					this.$emit('onDayClick', response);
				}
				console.log(response);
			},
			//改变年月
			changYearMonth(y, m) {
				this.dates = this.monthDay(y, m);
				this.y = y;
				this.m = m;
			},
			changeMonth(type) {
				if (type == 'pre') {
					if (this.m + 1 == 2) {
						this.m = 12;
						this.y = this.y - 1;
					} else {
						this.m = this.m - 1;
					}
				} else {
					if (this.m + 1 == 13) {
						this.m = 1;
						this.y = this.y + 1;
					} else {
						this.m = this.m + 1;
					}
				}
				this.dates = this.monthDay(this.y, this.m);
			},
			changeYear(type) {
				if (type === 'pre') {
					this.y = this.y - 1;
				} else {
					this.y = this.y + 1;
				}
			},
			changeMonthView(e) {
				console.log(e.target);
				if (this.year_month === false) {
					this.m = parseInt(e.target.id.split('_')[1]) + 1;
					this.selectedMonth = this.y + '_' + this.m;
					this.dates = this.monthDay(this.y, this.m);
				}
				this.year_month = !this.year_month;
			}
		}
	};
</script>

<style lang="scss" scoped>
	.calendar-wrapper {
		margin-top: 70rpx;
		color: #bbb7b7;
		font-size: 28rpx;
		text-align: center;
		background-color: #fff;
		padding-bottom: 10rpx;

		.header {
			display: flex;
			align-items: center;
			justify-content: center;
			height: 120rpx;
			color: #42464A;
			font-size: 32rpx;
			font-weight: bold;
			margin-top: 5rpx;
			width: 100vw;

			// border-bottom: 2rpx solid #f2f2f2;
			.pre,
			.next {
				color: #4d7df9;
				font-size: 28rpx;
				font-weight: normal;
				padding: 8rpx 15rpx;
				border-radius: 10rpx;
				border: 2rpx solid #dcdfe6;
			}

			.pre {
				margin-right: 30rpx;
			}

			.next {
				margin-left: 30rpx;
			}
		}

		.week {
			display: flex;
			align-items: center;
			height: 80rpx;
			line-height: 80rpx;
			border-bottom: 1rpx solid rgba(255, 255, 255, 0.2);

			view {
				flex: 1;
			}
		}

		.content {
			position: relative;
			overflow: hidden;
			transition: height 0.4s ease;

			.days {
				transition: top 0.3s;
				display: flex;
				align-items: center;
				flex-wrap: wrap;
				position: relative;

				.item {
					position: relative;
					display: block;
					height: 80rpx;
					line-height: 80rpx;
					width: calc(100% / 7);

					.day {
						font-style: normal;
						display: inline-block;
						vertical-align: middle;
						width: 60rpx;
						height: 60rpx;
						line-height: 60rpx;
						overflow: hidden;
						border-radius: 60rpx;

						// &.choose {
						//     background-color: #4d7df9;
						//     color: #fff;
						// }

						&.nolm {
							color: #fff;
							opacity: 0.3;
						}
					}

					.isWorkDay {
						color: #42464a;
					}

					.notSigned {
						font-style: normal;
						width: 8rpx;
						height: 8rpx;
						background: #fa7268;
						border-radius: 10rpx;
						position: absolute;
						left: 50%;
						bottom: 0;
						pointer-events: none;
					}

					.today {
						border: 1rpx solid #ffaaff;
						border-radius: 60rpx;
					}

					.recordedDay {
						color: #fff;
						background-color: #ffaaff;
					}

					.workDay {
						font-style: normal;
						width: 8rpx;
						height: 8rpx;
						background: #4d7df9;
						border-radius: 10rpx;
						position: absolute;
						left: 50%;
						bottom: 0;
						pointer-events: none;
					}

					.markDay {
						font-style: normal;
						width: 8rpx;
						height: 8rpx;
						background: #fc7a64;
						border-radius: 10rpx;
						position: absolute;
						left: 50%;
						bottom: 0;
						pointer-events: none;
					}
				}
			}
		}

		.hide {
			height: 80rpx !important;
		}

		.weektoggle {
			width: 85rpx;
			height: 32rpx;
			position: relative;
			bottom: -42rpx;

			&.down {
				transform: rotate(180deg);
				bottom: 0;
			}
		}
	}

	.test-block {
		width: 100vw;
		margin: auto;
		color: #42464a;

		.month-card {
			width: 23%;
			height: calc(420rpx / 3);
			margin: 25rpx 1%;
			border-radius: 10rpx;
			float: left;
			text-align: center;
			font-size: 34rpx;
		}

		.selectedMonth {
			background-color: #FBE9F5;
		}

		.inner-text {
			margin: 22rpx auto;
		}
	}



	.year-view,
	.month-view {
		position: absolute; // 避免动画出现意外效果
		margin: auto; // 居中显示
	}

	.header-ym {
		font-size: 40rpx;
		display: inline-block;
		margin: 0rpx 100rpx;
	}




	.year-panel-enter-active,
	.month-panel-enter-active {
		transition: all 2.0s;
	}

	.year-panel-enter {
		transform: translateY(100rpx);
		opacity: 0;
	}

	.month-panel-enter {
		transform: translateY(-100rpx);
		opacity: 0;
	}
</style>
