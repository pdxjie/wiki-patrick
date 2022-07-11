<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header />
    <router-view />
    <the-footer />
  </a-layout>
</template>
<script>
import TheHeader from "@/components/the-header";
import TheFooter from "@/components/the-footer";
export default {
  name: 'app',
  components: { TheFooter, TheHeader },
  data() {
    return {
      collapsed: false,
    };
  },
};
</script>

<style>
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 28px 16px 0;
  float: left;
}

</style>
