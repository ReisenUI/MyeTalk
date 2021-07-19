<template>
	<view>
		<view class="notFound">
			<image src="/static/moodIcon/angry.png" mode="heightFix"></image>
			{{info}}
		</view>
	</view>
</template>

<script>
	export default {
		name: "not-found",
		props: {
			info: {
				type: String,
				default: '查找失败'
			}
		},
		data() {
			return {

			};
		}
	}
</script>

<style>
.notFound {
		position: absolute;
		box-sizing: border-box;
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		color: #787878;
		font-size: 28rpx;
	}

	.notFound image {
		margin-bottom: 20rpx;
		height: 300rpx;
	}
</style>
