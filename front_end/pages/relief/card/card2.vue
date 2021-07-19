<template>
	<view class="root">
		<view class="line1">
			<view class="card" @tap='chooseA'>
				<image
					src="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=513726311,2513881708&fm=26&gp=0.jpg">
				</image>
                A
			</view>
			<view class="card" @tap='chooseB'>
				<image
					src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1966311449,3396264889&fm=26&gp=0.jpg">
				</image>
				B
			</view>
		</view>
		<view class="line2">
			<view class="card" @tap='chooseC'>
				<image
					src="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2171703560,687666607&fm=26&gp=0.jpg">
				</image>
				C
			</view>
			<view class="card" @tap='chooseD'>
				<image
					src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3429779369,2097060606&fm=26&gp=0.jpg">
				</image>
				D
			</view>
		</view>
		<view class="answer" v-if="chooseid==1">
		    选这张牌表示你能过上衣食无忧的生活，富婆命的指数在80%左右，你会成为富婆的概率很高。
			但你未必是靠自身努力而实现经济富裕，更可能是因为你本来家境就不错。
			家人为你留下了一定的物业资产，足够让你生活无忧。
			此外还有一种可能是，你结婚的对象有稳固的财务基础。
			在金钱和物质上都可以满足你，带给你富足优渥的生活。
		</view>
		<view class="answer" v-if="chooseid==2">
			选这张牌表示,你将通过自身努力实现财务自由。
			可能富婆命的指数在90%左右。
			生活中的你理智沉稳，擅长以理性思维解决问题。
			所以，你是适合经商的人，也大有机会通过经商获得丰厚的收入。
			因此，若你有创业的打算，建议你不妨大胆试一试，获得成功的几率会较大。
		</view>
		<view class="answer" v-if="chooseid==3">
			选这张牌表示生活中的你不大懂得理财，可能是富婆命的指数在50%左右。
			你是活在当下的乐观主义者，追求自由。
			也正因为你有着活在当下的心态，所以你有什么想做的事会马上去做，
			买的东西会马上去买，不会考虑自己的经济能力，导致自己的财务状况不甚可观。
			你这辈子或许能活的随性自由，但若想财务富裕，首先还得学会合理分配自己的花销。
		</view>
		<view class="answer" v-if="chooseid==4">
			选这张牌表示你也许没有大富大贵的运气，可能富婆命的指数在30%左右。
			但你属于脚踏实地类型，不会好高骛远，你总是专注于自己的工作，在事业上一步一个脚印。
			希望能把自己的专业技能磨练好。所以在年轻时你也许会感觉自己的事业和财运都是 平平。
			可随着年纪的增长，你的专业技能也会越来越出色。届时就算你未能大富大贵，但要达到财务充裕也是不成问题。
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
		        chooseid:null
			}
		},
		methods: {
			chooseA(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=1;
			}
			},
			chooseB(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=2
			}
			},
			chooseC(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=3;
			}
			},
			chooseD(){
			if(this.chooseid!==null){
				this.chooseid=null
			}
			else{
				this.chooseid=4
			}
			}
			}
	}
</script>

<style lang="scss">
	page{
		background: #463267;
	}
	.answer{
		color: #fff;
		font-size: 40rpx;
		display: flex;
		justify-content: center;
		margin: 0 20rpx;
		padding: 0 20rpx;
		border: 1px solid #fff;
	}
	.root {
		background: #463267;
		width: 100%;
		height: 100%;
	}

	.line1 {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		margin-bottom: 10rpx;
		padding: 0 20rpx;
        margin-top: 20rpx;

	}

	.line2 {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		padding: 0 20rpx;


	}

	.card {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		justify-content: center;
		align-items: center;
		color: white;
		padding: 0 20rpx;
		width: 50%;
		height: 400rpx;
		image {
			width: 100%;
			height: 400rpx;
		}
	}
</style>
