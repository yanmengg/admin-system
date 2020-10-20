<template>
    <div id="blog" class="blog">
        <el-header style="padding: 0px;">
            <div class="header-box">
                <el-menu :default-active="menuActiveIndex" class="menu-box" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="index">首页</el-menu-item>
                    <el-submenu index="category">
                        <template slot="title">分类</template>
                        <el-menu-item v-for="category in categoryList" :key="category.id" :index="category.id">
                            {{category.name}}（{{category.articleCount}}）
                        </el-menu-item>
                    </el-submenu>
                    <el-menu-item index="document">文档</el-menu-item>
                </el-menu>
            </div>
        </el-header>
        <el-container>
            <el-container style="display: flex;">
                    <router-view></router-view>
            </el-container>
        </el-container>

    </div>
</template>

<script>
    import bus from '../../../api/bus';
    import BlogApi from '../../../api/business/blog';

    export default {
        data() {
            return {
                // menuActiveIndex:'index'
                categoryList: [],

            };
        },
        computed: {
            menuActiveIndex: function () {
                return this.$store.state.menuActiveIndex;
            },
            // menuActiveIndex: function () {
            //     通过中央事件总线插件vue-bus
            //     return this.indexValue;
            // }
        },
        created() {
            // bus.$on('changeMenuActiveIndex', key => {
            //     this.indexValue = key;
            // })
            this.getCategoryList();

        },
        methods: {
            handleSelect(key, keyPath) {
                if (key == 'index') {
                    // this.menuActiveIndex = "index";
                    this.$store.commit('changeMenuActiveIndex', 'index');
                    this.$router.push({path: `/blog/index`})
                    bus.$emit('selectCategory', '');
                }
                else if (keyPath[0] == 'category') {
                    this.$router.push({path: `/blog/index`, query: {cid: keyPath[keyPath.length - 1]}})
                }
            },
            getCategoryList() {
                BlogApi.getCategoryList().then((res) => {
                    if (res.error === false) {
                        this.categoryList = res.data;
                    } else {
                        this.$message.error(res.msg);
                    }
                }, (err) => {
                    this.$message.error(err.msg);
                })
            },

        }
    }
</script>

<style>
    #blog {
        height: 100%;
        overflow-y: auto;
        background: #f5f8f9;
    }

    .header-box {
        width: 100%;
        height: 60px;
        position: absolute;
        top: 0px;
        z-index: 1501;
        margin: 0px;
    }

    .menu-box {
        width: 100%;
    }

    .main-box {
        width: 100%;
        height: 100%;
    }

    .article-list {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
        margin-bottom: 300px;
    }

    .article-item {
        width: 100%;
        margin-bottom: 10px;
        cursor: pointer;
    }

    .article-time {
        font-size: 13px;
        color: #999;
    }

    .article-tag-box {
        font-size: 13px;
        margin-left: 5%;
    }

    .article-tag {
        margin-left: 2px;
    }

    .views-count {
        font-size: 12px;
        color: #999;
        float: right;
    }

    .article-bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .aside-left-box {
        display: flex;
        justify-content: center;
        height: 100%;
    }

    .aside-right-box {
        display: flex;
        justify-content: center;
        flex-direction: column;
        height: 100%;
    }

    .right-box-1 {
        height: 80%;
        width: 80%;
        margin-top: 5%;
    }

    .right-box-2 {
        height: 80%;
        width: 80%;
        margin-top: 5%;
    }

    .right-box-3 {
        height: 80%;
        width: 80%;
        margin-top: 5%;
        display: flex;
        justify-content: center;
    }

    .left-box-1 {
        height: 80%;
        width: 100%;
        margin-top: 5%;
        display: flex;
        justify-content: center;
    }

    .category-item {
        font-size: 15px;
        padding: 10px 0;
        cursor: pointer;
    }

    .category-active {
        color: #38b7ea;
    }

    .category-item a {
        color: #38b7ea;
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }
</style>

