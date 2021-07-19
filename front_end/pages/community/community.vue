<template>
	<view>
		<!-- 自定义导航栏 -->
		<view class="">
			<!-- @change="change" :current="activeIndex" -->
			<view class="navbar">
				<!-- <navigator class="navbar-headPhoto" url="../community-personalCenter/self-personalCenter/self-personalCenter"+"?userId=" +usrId_self>
					<image :src="userInfo.avatar" mode="aspectFit"></image>
				</navigator> -->
				<view class="navbar-headPhoto" @click="openselfPersonalCenter(userInfo)">
					<image :src="userInfo.avatar" mode="aspectFit"></image>
				</view>

				<!-- <scroll-view class="title-scroll">
					<view class="title-scroll-box">
						<view class="title-scroll-item" :class="{ active:titleIndex === index}"
							@click="clickTitle(item, index)" v-for="(item, index) in list" :key="index" @change="change"
							:titleIndex="titleIndex" :activeIndex="activeIndex">
							{{item.name}}
						</view>
					</view>
				</scroll-view> -->

				<view class="title-item">
					<view id="0" :class="[currentindex==0?'selected':'normal']" @click="changecurrentindex">
						解忧杂货铺
					</view>
					<view id="1" :class="[currentindex==1?'selected':'normal']" @click="changecurrentindex2">
						阿基米德专区
					</view>
				</view>

				<navigator class="navbar-message" url="community-message/community-message">
					<uni-icons type="chatboxes" size="50" color="#777"></uni-icons>
				</navigator>
			</view>
			<view :style="{height: navBarHeight+'px'}"></view>
		</view>

		<view class="tab" v-if="this.currentindex==0">
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
				<view id="0" :class="[bottomindex==0?'selected':'normal']" @click="clickLatest">最近更新</view>
				<view >另一个我</view>
				<view id="2" :class="[bottomindex==2?'selected':'normal']" @click="clickSelected">只看精选</view>
			</view>
		</view>

		<view class="tab" v-if="this.currentindex==1">
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
					<view class="tab-scroll-item" :class="{active:activeAjmdIndex === index}" @click="clickAjmdTab(item, index)"
						v-for="(item, index) in tabaAjmdList" :key="index">
						# {{item.topic_name}} #
					</view>
				</view>
			</scroll-view>

			<view class="tab-bottom">
				<view id="0" :class="[bottomAjmdIndex==0?'selected':'normal']" @click="clickAjmdLatest">最近更新</view>
				<view id="2" :class="[bottomAjmdIndex==2?'selected':'normal']" @click="clickAjmdSelected">只看精选</view>
			</view>
		</view>

		<view class="content" v-if="this.currentindex==0">
			<view class="listcard" v-for="(item, index1) in articleList" :key="item.article_id" :index="index1"
				@click="openPassageLingxian(item, index1)">

				<view class="listcard-head">
					<view class="head-Photo" @click.stop="openPersonal(item)">
						<image :src="item.avatar" mode="aspectFit"></image>
					</view>
					<view class="head-name" @click.stop="openPersonal(item)">{{item.nickname}}</view>
					<view class="head-more" @click.stop="openMenu(index1,item)">
						<uni-icons type="more-filled" size="40" color="#888"></uni-icons>
					</view>
				</view>

				<view class="myimgDV">
					<view v-if="imgList.length>0" class='receiveimage'>
						<block v-for="(item, ind) in imgList[index1]" :key="ind">
							<image class="imgitem"
								:style="{width: imgClass[index1].width, height: imgClass[index1].height}"
								:class="imgClass[index1].class" :src="item" :mode="imgClass[index1].mode"
								@click="previewpic(item,imgList[index1])"></image>
						</block>
					</view>
				</view>

				<view class="listcard-content">
					<view class="listcard-content_title">
						<view class="content_title">
							{{item.article.title}}
						</view>
						<view class="content_tag">
							{{item.article.tag}}
						</view>
					</view>
					<view class="listcard-content_describe">
						<text>{{item.article.content}}</text>
					</view>
				</view>

				<view class="listcard-bottom">
					<view class="listcard-bottom_time" v-for="(item, ind) in timeList[index1]" :key="ind">
						{{item}}
					</view>
					<view class="listcard-bottom_dianzan" @click.stop="likeTab(item)">
						<view class="zan">
							<image :src="item.started?star[1]:star[0]"></image>
						</view>
						<view>{{item.article.star}}</view>
					</view>
					<view class="listcard-bottom_pinglun">
						<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
						<view>{{item.article.comment}}</view>
					</view>
				</view>

			</view>
			<uni-load-more iconType="snow" :status="status"></uni-load-more>
		</view>

		<view class="content" v-if="this.currentindex==1">
			<view class="listcard" v-for="(item, index1) in articleAjmdList" :key="item.article_id" :index="index1"
				@click="openPassageLingxian(item, index1)">

				<view class="listcard-head">
					<view class="head-Photo" @click.stop="openPersonal(item)">
						<image :src="item.avatar" mode="aspectFit"></image>
					</view>
					<view class="head-name" @click.stop="openPersonal(item)">{{item.nickname}}</view>
					<view class="head-more" @click.stop="openMenu(index1,item)">
						<uni-icons type="more-filled" size="40" color="#888"></uni-icons>
					</view>
				</view>

				<view class="myimgDV">
					<view v-if="imgAjmdList.length>0" class='receiveimage'>
						<block v-for="(item, ind) in imgAjmdList[index1]" :key="ind">
							<image class="imgitem"
								:style="{width: imgAjmdClass[index1].width, height: imgAjmdClass[index1].height}"
								:class="imgAjmdClass[index1].class" :src="item" :mode="imgAjmdClass[index1].mode"
								@click="previewpic(item,imgAjmdList[index1])"></image>
						</block>
					</view>
				</view>

				<view class="listcard-content">
					<view class="listcard-content_title">
						<view class="content_title">
							{{item.article.title}}
						</view>
						<view class="content_tag">
							{{item.article.tag}}
						</view>
					</view>
					<view class="listcard-content_describe">
						<text>{{item.article.content}}</text>
					</view>
				</view>

				<view class="listcard-bottom">
					<view class="listcard-bottom_time" v-for="(item, ind) in timeAjmdList[index1]" :key="ind">
						{{item}}
					</view>
					<view class="listcard-bottom_dianzan" @click.stop="likeTab(item)">
						<view class="zan">
							<image :src="item.started?star[1]:star[0]"></image>
						</view>
						<view>{{item.article.star}}</view>
					</view>
					<view class="listcard-bottom_pinglun">
						<uni-icons class="ping" type="chat" size="34" color="#888"></uni-icons>
						<view>{{item.article.comment}}</view>
					</view>
				</view>

			</view>
			<uni-load-more iconType="snow" :status="status"></uni-load-more>
		</view>

		<view class="xinjian" @click="newPassage">
			<view class="xinjian_fixed">
				<uni-icons type="plusempty" size="70" color="#fff"></uni-icons>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				list: [{
					name: '解忧杂货铺'
				}, {
					name: '阿基米德专区'
				}],
				currentindex: 0,
				activeIndex: '',
				activeAjmdIndex: '',
				titleIndex: 0,
				bottomindex: 5,
				bottomAjmdIndex: 5,
				navBarHeight: 60,
				tabList: [],
				tabaAjmdList: [],
				baseUrl: 'https://zk123.top/mye',
				articleList: [],
				uploadList: [],
				imgList: [],
				imgClass: [],
				timeList: [],
				articleAjmdList: [],
				imgAjmdClass: [],
				imgAjmdList: [],
				timeAjmdList: [],
				star: ["../../static/relief/star.png", "../../static/relief/star2.png"],
				status: 'more',
				userId: 1,
				curTag: '',
				pageSize: 10,
				pageNumber: 0,
				pageAjmdNumber: 0,
				tagNumber: 0,
				userInfo: {},
				triggered: false,
				starring: false,
				regionState: 0,	// 1 Latest 2 Hotest
			};
		},
		onLoad() {
			this.getTabList();
			this.getArticleList();
			this.getUserInfo();
			uni.$on('ChooseImage',(res)=>{
					this.userInfo.avatar = res;
				});
		},
		onShow() {
			const _this = this;
			uni.$on('likeTab',(res)=>{
				let currentTarget;
				if(_this.currentindex === 0){
					currentTarget = _this.articleList
					_this.articleList[res.index].article.star = res.star;
				} else {
					currentTarget = _this.articleAjmdList
					_this.articleAjmdList[res.index].article.star = res.star;
				}
				uni.request({
					url: 'https://zk123.top/mye/c/judgeStar',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						id: currentTarget[res.index].article.article_id,
					},
					success: (e) => {
						currentTarget[res.index].started = e.data.data
					},
				})
			});
			uni.$on('postComment',(res)=>{
				let currentTarget;
				// console.log(res);
				if(_this.currentindex === 0){
					currentTarget = _this.articleList
					_this.articleList[res.index].article.comment = res.commentNum;
				} else {
					currentTarget = _this.articleAjmdList
					_this.articleAjmdList[res.index].article.comment = res.commentNum;
				}
			});
		},
		//下拉刷新
		onPullDownRefresh() {
			if(this.triggered === true){
				uni.stopPullDownRefresh();
				return;
			}
			console.log('下拉刷新');
			this.articleList.splice(0, this.articleList.length);
			this.uploadList.splice(0, this.uploadList.length);
			this.timeList.splice(0, this.timeList.length);
			this.imgClass.splice(0, this.imgClass.length);
			this.imgList.splice(0, this.imgList.length);
			this.articleAjmdList.splice(0, this.articleAjmdList.length);
			this.imgAjmdClass.splice(0, this.imgAjmdClass.length);
			this.imgAjmdList.splice(0, this.imgAjmdList.length);
			this.timeAjmdList.splice(0, this.timeAjmdList.length);
			this.pageNumber = 0;
			this.pageAjmdNumber = 0;
			this.tagNumber = 0;
			this.status = 'more';
			this.activeIndex = '';
			this.activeAjmdIndex = '';
			this.curTag = '';
			this.bottomindex = 5, this.bottomAjmdIndex = 5;
			this.getArticleList();
			uni.stopPullDownRefresh();
		},
		onReachBottom() {
			// this.getTabList();
			if(this.triggered === true){
				return ;
			}
			console.log(this.regionState, this.curTag);
			if(this.regionState === 0){
				if( this.curTag !== '' ) {	// 使用话题查询
					if ( this.currentindex == 0){
						this.getTabArticle( this.curTag );
					} else {
						this.getAjmdTabArticle( this.curTag );
					}
				} else {
					this.getArticleList();
				}
			} else if (this.regionState === 1){
				if( this.currentindex == 0 ){
					this.getArticleLatest();
				} else {
					this.getAjmdArticleLatest();
				}
			} else if (this.regionState === 2){
				if( this.currentindex == 0){
					this.getHotestAticle();
				} else {
					this.getAjmdHotestAticle();
				}
			}
		},
		methods: {
			clearAll(){
				this.articleList.splice(0, this.articleList.length);
				this.uploadList.splice(0, this.uploadList.length);
				this.timeList.splice(0, this.timeList.length);
				this.imgClass.splice(0, this.imgClass.length);
				this.imgList.splice(0, this.imgList.length);
				this.articleAjmdList.splice(0, this.articleAjmdList.length);
				this.imgAjmdClass.splice(0, this.imgAjmdClass.length);
				this.imgAjmdList.splice(0, this.imgAjmdList.length);
				this.timeAjmdList.splice(0, this.timeAjmdList.length);
				this.pageNumber = 0;
				this.pageAjmdNumber = 0;
				this.tagNumber = 0;
				this.status = '';
			},
			changecurrentindex: function(e) {
				if(this.triggered === true){
					return ;
				}
				this.clearAll();
				this.pageNumber = 0;
				this.pageAjmdNumber = 0;
				this.tagNumber = 0;
				this.regionState = 0;
				this.status = 'more';
				this.currentindex = e.target.id;
				this.curTag = '';
				this.activeIndex = '';
				this.activeAjmdIndex = '';
				this.getTabList();
				this.getArticleList();
			},
			changecurrentindex2: function(e) {
				if( this.triggered === true){
					return ;
				}
				this.clearAll();
				this.pageNumber = 0;
				this.pageAjmdNumber = 0;
				this.tagNumber = 0;
				this.regionState = 0;
				this.status = 'more';
				this.currentindex = e.target.id;
				this.curTag = '';
				this.activeIndex = '';
				this.activeAjmdIndex = '';
				this.getTabAjmdList();
				this.getArticleList();
			},
			clickTab(item, index) {
				if(this.triggered === true){
					return ;
				}
				console.log(item, index);
				this.activeIndex = index;
				// 加了这行
				this.curTag = item.topic_name;
				this.bottomindex = 5, this.bottomAjmdIndex = 5;
				this.articleList.splice(0, this.articleList.length);
				this.uploadList.splice(0, this.uploadList.length);
				this.timeList.splice(0, this.timeList.length);
				this.imgClass.splice(0, this.imgClass.length);
				this.imgList.splice(0, this.imgList.length);
				this.pageNumber = 0;
				this.regionState = 0;
				// 加了这行
				this.status = 'more';
				this.getTabArticle(item.topic_name);
			},
			clickAjmdTab(item, index) {
				if(this.triggered === true){
					console.log(this.triggered);
					return;
				}
				console.log(item, index);
				this.activeAjmdIndex = index;
				// 加了这行
				this.curTag = item.topic_name;
				this.bottomindex = 5, this.bottomAjmdIndex = 5;
				console.log(item.topic_id, item.topic_name);
				this.articleAjmdList.splice(0, this.articleAjmdList.length);
				this.uploadList.splice(0, this.uploadList.length);
				this.timeAjmdList.splice(0, this.timeAjmdList.length);
				this.imgAjmdClass.splice(0, this.imgAjmdClass.length);
				this.imgAjmdList.splice(0, this.imgAjmdList.length);
				this.pageAjmdNumber = 0;
				this.regionState = 0;
				// 加了这行
				this.status = 'more';
				this.getAjmdTabArticle(item.topic_name);
			},
			newPassage() {
				console.log('打开新建页面');
				uni.navigateTo({
					url: "../../pages/community-newPassage/community-newPassage"
				})
			},
			openPersonal(item) {
				console.log(item);
				const params = {
					userId: item.article.user_id,
					nickname: item.nickname,
				}
				uni.navigateTo({
					url: "../community-personalCenter/community-personalCenter?params="+JSON.stringify(params)
				})
			},
			openselfPersonalCenter(userInfo) {
				console.log(userInfo);
				const params = {
					userId: userInfo.id,
					nickname: userInfo.nickname,
				}
				uni.navigateTo({
					url: "../community-personalCenter/community-personalCenter?params="+JSON.stringify(params)
				})
			},
			openPassageLingxian(item, index) {
				var articleId = item.article.article_id;
				uni.navigateTo({
					url: "../../pages/community-passage/passage-lingxian/passage-lingxian" +
						"?articleId=" +
						articleId + "&index=" + index 
				})
			},
			previewpic(cind, clist) {
				uni.previewImage({
					urls: clist,
					current: cind,
					indicator: 'default'
				});
			},
			getheight(imgCount) {
				if (imgCount === 0){
					return {
						width: '0%',
						height: '0%',
						class: 'nopic',
						mode: 'aspectFill',
					};
				} else if (imgCount === 1) {
					return {
						width: '100%',
						height: '500rpx',
						class: 'onepic',
						mode: 'aspectFill'
					};
				} else if (imgCount === 2 || imgCount === 4) {
					return {
						width: '49.2%',
						height: '330rpx',
						class: 'doublepic',
						mode: 'aspectFill'
					};
				} else {
					return {
						width: '32.5%',
						height: '220rpx',
						class: 'triplepic',
						mode: 'aspectFill'
					};
				}
			},
			//热门话题列表
			getTabList() {
				var _self = this;
				// this.tagNumber ++;
				uni.request({
					url: this.baseUrl + '/c/topic', //接口地址
					data: {
						region: 0,
						limit: 30,
						// offset: this.tagNumber
						offset: 1
					},
					success: (res) => {
						// 请求成功之后将文章列表数据赋值给tabList
						_self.tabList = res.data.data.ratingList; //根据API数据找到对应的集合
					}
				});
			},
			getTabAjmdList() {
				var _self = this;
				uni.request({
					url: this.baseUrl + '/c/topic', //接口地址
					data: {
						region: 1,
						limit: 30,
						offset: 1,
					},
					success: (res) => {
						console.log(res.data);
						// 请求成功之后将文章列表数据赋值给tabList
						_self.tabaAjmdList = res.data.data.ratingList; //根据API数据找到对应的集合
					}
				});
			},
			clickLatest(e) {
				if(this.triggered === true){
					return ;
				}
				this.pageNumber = 0;
				this.bottomindex = e.target.id;
				this.articleList.splice(0, this.articleList.length);
				this.timeList.splice(0, this.timeList.length);
				this.imgClass.splice(0, this.imgClass.length);
				this.imgList.splice(0, this.imgList.length);
				this.activeIndex = '';
				this.regionState = 1;
				this.getArticleLatest();
			},
			clickAjmdLatest(e) {
				if(this.triggered === true){
					return ;
				}
				console.log(e.target.id);
				this.bottomAjmdIndex = e.target.id;
				this.articleAjmdList.splice(0, this.articleAjmdList.length);
				this.timeAjmdList.splice(0, this.timeAjmdList.length);
				this.imgAjmdClass.splice(0, this.imgAjmdClass.length);
				this.imgAjmdList.splice(0, this.imgAjmdList.length);
				this.activeIndex = '';
				this.pageAjmdNumber = 0;
				this.regionState = 1;
				this.getAjmdArticleLatest();
			},
			clickSelected(e) {
				if(this.triggered === true){
					return ;
				}
				console.log(e.target.id);
				this.bottomindex = e.target.id;
				this.articleList.splice(0, this.articleList.length);
				this.timeList.splice(0, this.timeList.length);
				this.imgClass.splice(0, this.imgClass.length);
				this.imgList.splice(0, this.imgList.length);
				this.activeIndex = '';
				this.pageNumber = 0;
				this.regionState = 2;
				this.getHotestAticle();
			},
			clickAjmdSelected(e){
				if(this.triggered === true){
					return ;
				}
				console.log(e.target.id);
				this.bottomAjmdIndex = e.target.id;
				this.articleAjmdList.splice(0, this.articleAjmdList.length);
				this.timeAjmdList.splice(0, this.timeAjmdList.length);
				this.imgAjmdClass.splice(0, this.imgAjmdClass.length);
				this.imgAjmdList.splice(0, this.imgAjmdList.length);
				this.activeIndex = '';
				this.pageAjmdNumber = 0;
				this.regionState = 2;
				this.getAjmdHotestAticle();
			},
			likeTab(item) {
				uni.showLoading({
					title: "加载中..."
				})
				if(this.starring === true){
					return ;
				}
				this.starring = true;
				const _this = this;
				var is_star = item.started;
				uni.request({
					url: 'https://zk123.top/mye/c/star',
					method: 'POST',
					header: {
						Authorization: uni.getStorageSync('token'),
						'content-type': 'application/x-www-form-urlencoded'
					},
					data: {
						id: item.article.article_id,
						flag: '0'
					},
					success: (res) => {
						uni.hideLoading();
						console.log(res);
						if (is_star == false) {
							item.started = true;
							console.log('now:' + item.started);
							item.article.star = item.article.star + 1;
							uni.showToast({
								title: '点赞成功'
							})
						} else {
							item.started = false;
							console.log('now:' + item.started);
							item.article.star = item.article.star - 1;
							uni.showToast({
								title: '取消点赞'
							})
						}
						_this.starring = false;
					},
					fail: (err) => {
						uni.hideLoading();
						console.log(err)
						return uni.showToast({
							title: '请求失败'
						})
					}
				})
			},
			//热门话题对应帖子
			getTabArticle(tagName) {
				var _this = this;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				var j = 0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article', //接口地址
					method: 'GET',
					data: {
						userId: _this.userId,
						tag: tagName,
						limit: _this.pageSize,
						offset: ++_this.pageNumber
					},
					success: (res) => {
						_this.uploadList = res.data.data.articleList;
						const totalSize = res.data.data.totalSize;
						if(Math.ceil(totalSize / _this.pageSize) < _this.pageNumber){
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							(async ()=>{
								let index = _this.articleList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.expire_time == '0'){
											if(_this.uploadList[i].article.pic_url === null){
												_this.imgList.push([]);
												_this.imgClass.push(_this.getheight(0));
											} else {
												_this.imgList.push(_this.uploadList[i].article.pic_url.split(","));
												_this.imgClass.push(this.getheight(_this.imgList[index].length));
											}
											index++;
											_this.timeList.push(_this.uploadList[i].article.article_id.slice(11, 16));
											_this.articleList.push(_this.uploadList[i]);
										}
									}, ()=>{
										console.log( "err" );
									})
								};
								console.log("9998")
								_this.triggered = false;
								_this.status = 'more';
							})();
						}
					}
				});
			},
			getAjmdTabArticle(tagName) {
				var _this = this;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				var j = 0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article', //接口地址
					method: 'GET',
					data: {
						userId: _this.userId,
						tag: tagName,
						limit: _this.pageSize,
						offset: ++_this.pageAjmdNumber
					},
					success: (res) => {
						console.log(res.data);
						let imageAjmdList = [];
						_this.uploadList = res.data.data.articleList;
						const totalSize = res.data.data.totalSize;
						if(Math.ceil(totalSize / _this.pageSize) < _this.pageAjmdNumber){
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							(async ()=>{
								let index = _this.articleAjmdList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.expire_time === '1'){
											if(_this.uploadList[i].article === null){
												_this.imgAjmdList.push([]);
												_this.imgAjmdClass.push(this.getheight(0));
											} else {
												_this.imgAjmdList.push(_this.uploadList[i].article.pic_url.split(","));
												_this.imgAjmdClass.push(_this.getheight(_this.imgAjmdList[index].length));
											}
											index++;
											_this.timeAjmdList.push(_this.uploadList[i].article.article_id.slice(11,16));
											_this.articleAjmdList.push(_this.uploadList[i]);
										}
									}, ()=>{
										console.log( "err" );
									})
								};
								console.log("9998")
								_this.triggered = false;
								_this.status = 'more';
							})();
						}
					}
				});
			},
			getArticleList(tagName) {
				var _this = this;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				_this.status = 'loading';
				console.log(_this.pageSize);
				uni.request({
					url: this.baseUrl + '/c/article', //接口地址
					method: 'GET',
					data: {
						userId: _this.userId,
						tag: tagName,
						limit: _this.pageSize * 2, 
						offset: ++_this.pageNumber
					},
					success: (res) => {
						uni.stopPullDownRefresh();
						_this.uploadList = res.data.data.articleList;
						let imageList = [];
						let imageAjmdList = [];
						console.log(_this.uploadList);
						if (Math.ceil(res.data.data.totalSize / this.pageSize) < this.pageNumber) {
							console.log('1111111111111111');
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							var j = 0;
							var k = _this.articleList.length;
							var h = _this.articleAjmdList.length;
							console.log(h);
							(async ()=>{
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										let expire_time = _this.uploadList[i].article.expire_time;
										if (_this.currentindex == 0) {
											if(expire_time === '0'){
												if(_this.uploadList[i].article.pic_url === null){
													_this.imgList.push([]);
													_this.imgClass.push(_this.getheight(0));
												} else {
													_this.imgList.push(_this.uploadList[i].article.pic_url.split(","));
													_this.imgClass.push(_this.getheight(_this.imgList[k].length));
												}
												k++;
												_this.timeList.push(_this.uploadList[i].article.article_id.slice(11, 16));
												_this.articleList.push(_this.uploadList[i]);
											}
										} else {
											if( expire_time === '1'){
												if(_this.uploadList[i].article.pic_url === null){
													_this.imgAjmdList.push([]);
													_this.imgAjmdClass.push(_this.getheight(0));
												} else {
													_this.imgAjmdList.push(_this.uploadList[i].article.pic_url.split(","));
													_this.imgAjmdClass.push(_this.getheight(_this.imgAjmdList[h].length));
												}
												h++;
												_this.timeAjmdList.push(_this.uploadList[i].article.article_id.slice(11,16));
												_this.articleAjmdList.push(_this.uploadList[i]);
											}
										}
									}, ()=>{
										console.log( "error" );
									});
								}
								console.log(9998);
								_this.triggered = false;
								_this.status = 'more';
							})();
						}
					}
				});
			},
			getArticleLatest() {
				var _this = this;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				var j = 0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article/latest', //接口地址
					method: 'GET',
					data: {
						limit: _this.pageSize,
						offset: ++_this.pageNumber,
						region: 0
					},
					success: (res) => {
						_this.uploadList = res.data.data.articleList;
						const totalSize = res.data.data.totalSize;
						if(Math.ceil(totalSize / _this.pageSize) < _this.pageNumber) {
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							(async ()=>{
								let index = _this.articleList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									let imgList = [];
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.expire_time === '0'){
											if(_this.uploadList[i].article.pic_url === null){
												_this.imgList.push([]);
												_this.imgClass.push(_this.getheight(0));
											} else {
												_this.imgList.push(_this.uploadList[i].article.pic_url.split(","));
												_this.imgClass.push(this.getheight(_this.imgList[index].length));
											}
											index++;
											_this.timeList.push(_this.uploadList[i].article.article_id.slice(11, 16));
											_this.articleList.push(_this.uploadList[i]);
										}
									}, ()=>{
										console.log( "error" );
									});
								};
								console.log(9998);
								
								_this.triggered = false;
								_this.status = 'noMore';
							})();
						}
					}
				});
			},
			getAjmdArticleLatest(){
				var _this = this;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				var j = 0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article/latest', //接口地址
					method: 'GET',
					data: {
						limit: _this.pageSize,
						offset: ++_this.pageAjmdNumber,
						region: 1
					},
					success: (res) => {
						console.log(res.data);
						_this.uploadList = res.data.data.articleList;
						const totalSize = res.data.data.totalSize;
						if(Math.ceil(totalSize / _this.pageSize) < _this.pageAjmdNumber){
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							(async ()=>{
								let index = _this.articleAjmdList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.expire_time === '1'){
											if(_this.uploadList[i].article.pic_url === null){
												_this.imgAjmdList.push([]);
												_this.imgAjmdClass.push(_this.getheight(0));
											} else {
												_this.imgAjmdList.push(_this.uploadList[i].article.pic_url.split(","));
												_this.imgAjmdClass.push(this.getheight(_this.imgAjmdList[index].length));
											}
											index++;
											_this.timeAjmdList.push(_this.uploadList[i].article.article_id.slice(11,16));
											_this.articleAjmdList.push(_this.uploadList[i]);
										}
									}, ()=>{
										console.log( "error" );
									});
								}
								console.log(9998);
								_this.triggered = false;
							})();
						}
					}
				})
			},
			getHotestAticle() {
				var _this = this;
				var j = 0;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				// _this.pageNumber ++;
				console.log(_this.pageNumber);
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article/hottest', //接口地址
					method: 'GET',
					data: {
						limit: _this.pageSize,
						offset: ++_this.pageNumber,
						region: 0
					},
					success: (res) => {
						console.log(res.data);
						_this.uploadList = res.data.data.articleList;
						const totalSize = res.data.data.totalSize;
						if(Math.ceil(totalSize / _this.pageSize) < _this.pageNumber){
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							(async ()=>{
								let index = _this.articleList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.expire_time === '0'){
											if(_this.uploadList[i].article.pic_url === null){
												_this.imgList.push([]);
												_this.imgClass.push(_this.getheight(0));
											} else {
												_this.imgList.push(_this.uploadList[i].article.pic_url.split(","));
												_this.imgClass.push(this.getheight(_this.imgList[index].length));
											}
											index++;
											_this.timeList.push(_this.uploadList[i].article.article_id.slice(11, 16));
											_this.articleList.push(_this.uploadList[i]);
										}
									}, ()=>{
										console.log( "err" );
									})
								}
								console.log(9998);
								_this.triggered = false;
							})();
						}
					}
				});
			},
			getAjmdHotestAticle(){
				var _this = this;
				if(_this.triggered === true || _this.status === 'noMore'){
					return;
				}
				_this.triggered = true;
				var j = 0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article/hottest', //接口地址
					method: 'GET',
					data: {
						limit: _this.pageSize,
						offset: ++_this.pageAjmdNumber,
						region: 1
					},
					success: (res) => {
						console.log(res.data);
						_this.uploadList = res.data.data.articleList;
						const totalSize = res.data.data.totalSize;
						if(Math.ceil(totalSize / _this.pageSize) < _this.pageAjmdNumber){
							_this.status = 'noMore';
							_this.triggered = false;
						} else {
							(async ()=>{
								let index = _this.articleAjmdList.length;
								for (let i = 0; i < _this.uploadList.length; i++) {
									await this.judgeStars(_this.uploadList[i].article.article_id, i).then(()=>{
										if(_this.uploadList[i].article.expire_time === '1'){
											if(_this.uploadList[i].article.pic_url === null){
												_this.imgAjmdList.push([]);
												_this.imgAjmdClass.push(_this.getheight(0));
											} else {
												_this.imgAjmdList.push(_this.uploadList[i].article.pic_url.split(","));
												_this.imgAjmdClass.push(this.getheight(_this.imgAjmdList[index].length));
											}
											index++;
											_this.timeAjmdList.push(_this.uploadList[i].article.article_id.slice(11,16));
											_this.articleAjmdList.push(_this.uploadList[i]);
										}
									}, ()=>{
										console.log( "err" );
									})
								}
								console.log(9998);
								_this.triggered = false;
							})();
						}
					}
				});
			},
			async judgeStars(articleId, index){
				const _this = this;
				return new Promise((resolve, reject)=>{
					uni.request({
						url: 'https://zk123.top/mye/c/judgeStar',
						header: {
							Authorization: uni.getStorageSync('token'),
							'content-type': 'application/x-www-form-urlencoded'
						}, 
						data: {
							id: articleId
						}, 
						success: (e) => {
							_this.uploadList[index].started = e.data.data;
							resolve();
						}, 
						fail: ()=>{
							reject();
						}
					})
				})
			},
			/* clickAnotherMe(e) {
				console.log(e.target.id);
				this.bottomindex = e.target.id;
				this.articleList.splice(0, this.articleList.length);
				this.timeList.splice(0, this.timeList.length);
				this.imgClass.splice(0, this.imgClass.length);
				this.imgList.splice(0, this.imgList.length);
				this.getAnotherMe();
			},
			getAnotherMe() {
				var _this = this;
				var j = 0;
				_this.status = 'loading';
				uni.request({
					url: this.baseUrl + '/c/article/anotherMe', //接口地址
					method: 'GET',
					data: {
						offset: 1
					},
					success: (res) => {
						console.log(res.data);
						_this.articleList = res.data.data.articleList;
						for (let i = 0; i < _this.articleList.length; i++) {
							uni.request({
								url: 'https://zk123.top/mye/c/judgeStar',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									id: _this.articleList[i].article.article_id
								},
								success: (e) => {
									_this.articleList[i].started = e.data.data;
									j++;
								},
							})
							_this.imgList.push(_this.articleList[i].article.pic_url.split(","));
							_this.timeList.push(_this.articleList[i].article.article_id.slice(11, 16));
							_this.imgClass.push(this.getheight(_this.imgList[i].length));
						};
						_this.status = 'noMore';
					},
					fail: (err) => {
						console.log(err)
						return uni.showToast({
							title: '请求失败'
						})
					}
				});
			}, */
			openMenu(index1, item) {
				var _this = this;
				console.log(index1);
				// 帖子的userId  数据的类型是string 需要先转换一下数据类型
				var article_userId = Number(item.article.article_id.slice(0, 5));
				var usrId = _this.userInfo.id;
				// 看是否为自己发的帖子
				if (_this.userInfo.id === article_userId) {
					uni.showActionSheet({
						itemList: ['删除帖子', '复制内容'],
						success: function(res) {
							var index = res.tapIndex;
							if (index === 0) {
								_this.deleteArticle(index1, item);
							} else if (index === 1) {
								console.log('复制内容');
								uni.showToast({
									title: '内容已复制'
								})
							}
						},
						fail: function(res) {
							console.log(res.errMsg);
						}
					});
				} else {
					uni.showActionSheet({
						itemList: ['复制内容'],
						success: function(res) {
							console.log(res.tapIndex);
							var index = res.tapIndex;
							if (index === 0) {
								console.log('复制内容');
								uni.showToast({
									title: '内容已复制'
								})
							}
						},
						fail: function(res) {
							console.log(res.errMsg);
						}
					});
				}
			},
			deleteArticle(index, item) {
				var _this = this;
				uni.showModal({
					title: '友情提示',
					content: '确定要删除这条帖子吗',
					success: function(res) {
						if (res.confirm) {
							uni.request({
								url: 'https://zk123.top/mye/c/article/delete',
								method: 'POST',
								header: {
									Authorization: uni.getStorageSync('token'),
									'content-type': 'application/x-www-form-urlencoded'
								},
								data: {
									articleId: item.article.article_id,
								},
								success: (res) => {
									_this.articleList.splice(index, 1);
									_this.uploadList.splice(index, 1);
									_this.timeList.splice(index, 1);
									_this.imgClass.splice(index, 1);
									_this.imgList.splice(index, 1);
									uni.showToast({
										title: '删除成功'
									})
								},
								fail: (err) => {
									console.log(err)
									return uni.showToast({
										title: '请求失败'
									})
								}
							})
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			getUserInfo() {
				const _this = this
				uni.request({
					url: 'https://zk123.top/mye/people/info',
					data: {},
					header: {
						Authorization: uni.getStorageSync('token')
					},
					method: 'GET',
					success: (res) => {
						_this.userInfo = res.data.data;
					}
				})
			},
		}
	}
</script>

<style lang="scss">
	.navbar {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 120upx;
		background-color: #FFFFFF;
		position: fixed;
		top: --window-top;
		/* css变量 */
		left: 0;
		top: 0;
		z-index: 99;
		text-align: center;

		.navbar-headPhoto image {
			width: 60rpx;
			height: 60rpx;
			border-radius: 50%;
			position: absolute;
			top: 50%;
			margin-top: -30rpx;
			left: 30rpx;
		}

		.title-item {
			display: flex;
			justify-content: center;
			font-size: 34rpx;

			.selected {
				margin: 0 30rpx;
				color: #0092d0;
				border-bottom: 2px solid #22a3d0;
				padding-bottom: 8rpx;
			}

			.normal {
				margin: 0 30rpx;
				color: #777;
			}

			/* .title-jieyou{
				margin: 0 30rpx;
				padding-bottom: 8rpx;
				border-bottom: 2px solid #22a3d0;
			}
			
			.title-ajimide{
				margin: 0 30rpx;
				padding-bottom: 8rpx;
				color: #666;
			} */
		}

		/* .title-scroll {
			display: flex;
			flex-direction: row;
			box-sizing: border-box;
			margin: 10rpx 120rpx;

			.title-scroll-box {
				display: flex;
				align-items: center;
				justify-content: space-around;
				text-align: center;
				flex-wrap: nowrap;
				box-sizing: border-box;
				padding: 10rpx;

				.title-scroll-item {
					font-size: 34rpx;
					padding: 14rpx 0;
					color: #777;

					&.active {
						border-bottom: 2px solid #22a3d0;
						color: #000;
					}
				}
			} 
		}*/

		.navbar-message {
			position: absolute;
			right: 30rpx;
		}
	}



	.tab {
		width: 100%;
		box-sizing: border-box;

		.tab_ajmd_top {
			width: 97%;
			height: 60rpx;
			background: #FFFFFF;
			display: flex;
			margin: 10rpx;
			margin-top: 20rpx;
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

					&.active {
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

			.selected {
				margin: 0 10rpx;
				color: #0092d0;
			}

			.normal {
				margin: 0 10rpx;
				color: #777;
			}
		}
	}

	.listcard {
		display: flex;
		flex-direction: column;
		padding: 20rpx;
		margin: 16rpx 12rpx;
		border-radius: 8rpx;
		box-shadow: 0 0 10rpx 2rpx rgba($color: #000000, $alpha: 0.1);
		box-sizing: border-box;
		background: #FFFFFF;

		.listcard-head {
			display: flex;
			float: left;
			width: 100%;
			height: 75rpx;
			justify-content: space-between;
			align-items: center;
			position: relative;

			.head-Photo {
				width: 60rpx;
				height: 60rpx;
				overflow: hidden;

				image {
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}

			.head-name {
				font-size: 30rpx;
				color: #555555;
				position: absolute;
				left: 80rpx;
			}
		}

		.myimgDV {

			.receiveimage {
				margin-top: 8rpx;
				display: flex;
				flex-wrap: wrap;

				.onepic {
					margin-bottom: 8rpx;

					&:nth-child(3n) {
						margin-right: 0;
					}
				}

				.doublepic {
					margin-right: 8rpx;
					margin-bottom: 8rpx;

					&:nth-child(2n) {
						margin-right: 0;
					}
				}

				.triplepic {
					margin-right: 8rpx;
					margin-bottom: 8rpx;

					&:nth-child(3n) {
						margin-right: 0;
					}
				}
			}
		}

		.listcard-content {
			display: flex;
			flex-direction: column;
			width: 100%;

			.listcard-content_title {
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-weight: 400;
				margin: 10rpx 0;

				.content_title {
					font-size: 32rpx;
					color: #000;
					line-height: 1.8;
				}

				.content_tag {
					padding: 8rpx 24rpx;
					border-radius: 32rpx;
					background-color: #ccd8f2;
					box-shadow: 0 0 10rpx 2rpx rgba($color: #1294ca, $alpha: 0.1);
					color: #666;
					font-size: 28rpx;
				}
			}

			.listcard-content_describe {
				font-size: 28rpx;
				color: #444;
				line-height: 1.5;

				text {
					overflow: hidden;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-line-clamp: 3;
					-webkit-box-orient: vertical;
				}
			}
		}

		.listcard-bottom {
			display: flex;
			float: left;
			width: 100%;
			height: 75rpx;
			// justify-content: space-around;
			align-items: center;
			position: relative;
			font-size: 26rpx;
			color: #888888;
			position: relative;

			.listcard-bottom_dianzan {
				display: flex;
				position: absolute;
				right: 100rpx;

				.zan {
					margin-top: 2rpx;
					padding-right: 10rpx;
					width: 40rpx;
					height: 40rpx;

					image {
						width: 100%;
						height: 100%;
					}
				}
			}

			.listcard-bottom_pinglun {
				display: flex;
				position: absolute;
				right: 20rpx;

				.ping {
					margin-top: 5rpx;
					padding-right: 10rpx;
				}
			}
		}
	}


	.xinjian {
		position: fixed;
		left: 50%;
		margin-left: -50rpx;
		bottom: 110rpx;
		z-index: 99;

		.xinjian_fixed {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 50px;
			height: 50px;
			border-radius: 50%;
			background-color: rgba($color: #000000, $alpha: 0.5);
		}
	}
</style>
