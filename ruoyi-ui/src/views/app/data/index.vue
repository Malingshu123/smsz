<template>
  <div class="container">
    <div class="grid">
      <div
        class="function-card"
        v-for="(item, index) in flatFunctions"
        :key="index"
      >
        <img class="card-img" :src="item.image" alt="功能图标">
        <div class="content">
          <span class="title">{{ item.title }}</span>
          <a
            class="link-btn"
            :href="item.link"
            target="_blank"
            rel="noopener"
          >
            立即获取
          </a>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import { listResourceByType } from "@/api/system/resource";
import defAva from '@/assets/images/app.jpg'

export default {
  data() {
    return {
      flatFunctions: []
    };
  },
  mounted() {
    this.fetchFunctions();
  },
  methods: {
    async fetchFunctions() {
      try {
        const type = 0;
        const response = await listResourceByType(type);
        if (response && response.rows) {
          this.flatFunctions = response.rows.map(item => ({
            title: item.title,
            link: item.url,
            image: defAva // 默认图片
          }));
        } else {
          console.error('接口返回数据格式不正确');
        }
      } catch (error) {
        console.error('请求错误:', error);
      }
    }
  }
};


</script>

<style scoped>
.container {
  padding: 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.function-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 20px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  min-height: 100px;
  display: flex;
  align-items: center;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.05);
  border: 1px solid #eee;
}

.function-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.card-img {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  margin-right: 20px;
  object-fit: cover;
  flex-shrink: 0;
}

.content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex: 1;
  min-width: 0;
}

.title {
  font-size: 16px;
  color: #333;
  flex: 1;
  margin-right: 15px;
  line-height: 1.5;
  font-weight: 600;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.link-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white !important;
  padding: 10px 24px;
  border-radius: 8px;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.3s;
  white-space: nowrap;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.link-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  opacity: 0.9;
}

@media (max-width: 1200px) {
  .grid {
    grid-template-columns: repeat(2, 1fr);
  }
  .card-img {
    width: 70px;
    height: 70px;
  }
}

@media (max-width: 768px) {
  .grid {
    grid-template-columns: 1fr;
  }
  .function-card {
    min-height: 130px;
    padding: 15px;
  }
  .card-img {
    width: 60px;
    height: 60px;
    margin-right: 15px;
  }
  .title {
    font-size: 15px;
  }
  .link-btn {
    padding: 8px 18px;
  }
}
</style>
