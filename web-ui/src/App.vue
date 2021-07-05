<template>
  <div id="app" class="c-app">
    <c-wrapper>
      <div class="c-body">
        <main class="c-main">
          <c-container fluid>
            <FileUpload/>
          </c-container>
        </main>
      </div>
    </c-wrapper>
  </div>
</template>

<script>
import FileUpload from './components/FileUpload.vue';

export default {
  name: 'App',
  components: {
    FileUpload,
  },
};
</script>

<style lang="scss">
@import 'assets/scss/style';
</style>
