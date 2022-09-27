<template>
  <nav class="navbar navbar-dark bg-primary">
    <div class="container-fluid">
    <div><span class="navbar-brand">Demo</span></div>
    <div>
        <router-link to="/" class="navbar-brand">Home</router-link>
        <router-link to="/create"  class="navbar-brand">Create</router-link>
    </div>
  </div>
  </nav>

  <div class="container"><router-view ></router-view></div>
    
</template> 
<script>
export default {
  
}
</script>
