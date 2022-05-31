<template>
  <div class="layout">
    <div class="nav">
      <div class="logo">StudBud</div>
      <div :class="['nav-item', current == 'kanban' && 'active']" @click="$router.push('kanban')"><img src="@/assets/calendar.png" alt="">Kanban</div>
      <div :class="['nav-item', current == 'pomodoro' && 'active']" @click="$router.push('pomodoro')"><img src="@/assets/chart.png" alt="">Pomodoro</div>
      <div :class="['nav-item', current == 'reading' && 'active']" @click="$router.push('reading')"><img src="@/assets/block.png" alt="">Reading List</div>
      <div class="time-zone">
        <img src="@/assets/logo.png" alt="">
        <a href="">Click to change time zone</a>
      </div>
    </div>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
export default {
  data () {
    return {
      current: 'kanban'
    }
  },
  watch: {
    $route: function () {
      this.current = this.$route.name
    }
  }
}
</script>

<style lang="scss" scoped>
.layout {
  display: flex;
  height: 100vh;
  .nav {
    position: relative;
    width: 180px;
    min-width: 180px;
    background: rgb(202, 202, 248);
    .logo {
      color: rgb(31, 39, 143);
      font-size: 28px;
      font-weight: bold;
      text-align: center;
      padding: 16px 0 32px;
    }
    .nav-item {
      display: flex;
      align-items: center;
      border-radius: 10px 0 0 10px;
      padding: 8px;
      margin: 0 0 32px 32px;
      color: rgb(138, 138, 166);
      font-size: 12px;
      cursor: pointer;
      img {
        width: 14px;
        height: 14px;
        margin: 0 4px 0 0;
      }
      &.active {
        background: #fff;
        color: rgb(31, 39, 143);
      }
    }
    .time-zone {
      display: flex;
      flex-direction: column;
      align-items: center;
      position: absolute;
      left: 0;
      right: 0;
      bottom: 0;
      padding: 0 0 32px;
      img {
        width: 50px;
      }
      a {
        font-size: 12px;
        margin: 16px 0 0;
      }
    }
  }
  main {
    flex: 1;
  }
}
</style>
