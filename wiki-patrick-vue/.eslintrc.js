module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    '@vue/typescript/recommended'
  ],
  parserOptions: {
    ecmaVersion: 2020
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'vue/no-unused-components': 'off',
    '@typescript-eslint/no-explicit-any': 'off',
    '@typescript-eslint/ban-ts-comment': 'off',
    'vue/no-unused-vars': 'off',
    'vue/no-multiple-template-root': 'off',
    'vue/no-v-model-argument': 'off',
    'vue/valid-v-slot': 'off',
    '@typescript-eslint/ban-types': 'off',
    'vue/no-deprecated-slot-attribute': 'off',
    'vue/no-v-for-template-key-on-child': 'off',
    'vue/no-parsing-error': 'off',
    'vue/no-deprecated-slot-scope-attribute': 'off',
    'vue/multi-word-component-names':'off',
    '@typescript-eslint/no-empty-function':'off'
  }
}
