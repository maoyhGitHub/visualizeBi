module.exports = {
  root: true,
  parserOptions: {
    parser: 'babel-eslint',
    sourceType: 'module'
  },
  env: {
    browser: true,
    node: true,
    es6: true,
  },
  extends: ['plugin:vue/recommended', 'eslint:recommended'],

  // add your custom rules here
  // it is base on https://github.com/vuejs/eslint-config-vue
  rules: {
    'vue/singleline-html-element-content-newline': 'off',
    'vue/multiline-html-element-content-newline': 'off',
    'vue/no-mutating-props': 'off',
    'vue/multi-word-component-names': 'off',
    'vue/require-default-prop': 'off',
    'vue/no-v-html': 'off',
    'accessor-pairs': 2,
    'arrow-spacing': [0, {
      'before': true,
      'after': true
    }],
    'block-spacing': [0, 'always'],
    'brace-style': [0, '1tbs', {
      'allowSingleLine': true
    }],
    'camelcase': [0, {
      'properties': 'always'
    }],
    'comma-dangle': [0, 'never'],
    'comma-spacing': [0, {
      'before': false,
      'after': true
    }],
    'comma-style': [2, 'last'],
    'constructor-super': 0,
    'curly': [0, 'multi-line'],
    'dot-location': [2, 'property'],
    'eol-last': 0,
    /*     'eqeqeq': ['error', 'always', {
      'null': 'ignore'
    }], */
    'generator-star-spacing': [2, {
      'before': true,
      'after': true
    }],
    'handle-callback-err': [2, '^(err|error)$'],
    'indent': [1, 2, {
      'SwitchCase': 1
    }],
    'jsx-quotes': [2, 'prefer-single'],
    'key-spacing': [0, {
      'beforeColon': false,
      'afterColon': true
    }],
    'keyword-spacing': [0, {
      'before': true,
      'after': true
    }],
    'new-cap': [2, {
      'newIsCap': true,
      'capIsNew': false
    }],
    'new-parens': 2,
    'no-array-constructor': 2,
    'no-caller': 2,
    'no-console': 'off',
    'no-class-assign': 2,
    'no-cond-assign': 2,
    'no-const-assign': 2,
    'no-control-regex': 0,
    'no-delete-var': 2,
    'no-dupe-args': 2,
    'no-dupe-class-members': 2,
    'no-dupe-keys': 2,
    'no-duplicate-case': 2,
    'no-empty-character-class': 2,
    'no-empty-pattern': 2,
    'no-eval': 0,
    'no-ex-assign': 2,
    'no-extend-native': 2,
    'no-extra-bind': 2,
    'no-extra-boolean-cast': 2,
    'no-extra-parens': [0, 'functions'],
    'no-fallthrough': 2,
    'no-floating-decimal': 2,
    'no-func-assign': 2,
    'no-implied-eval': 2,
    'no-inner-declarations': [2, 'functions'],
    'no-invalid-regexp': 2,
    'no-irregular-whitespace': 2,
    'no-iterator': 2,
    'no-label-var': 2,
    'no-labels': [0, {
      'allowLoop': false,
      'allowSwitch': false
    }],
    'no-lone-blocks': 2,
    'no-mixed-spaces-and-tabs': 2,
    'no-multi-spaces': 0,
    'no-multi-str': 2,
    'no-multiple-empty-lines': [0, {
      'max': 1
    }],
    'no-native-reassign': 2,
    'no-negated-in-lhs': 2,
    'no-new-object': 2,
    'no-new-require': 2,
    'no-new-symbol': 2,
    'no-new-wrappers': 2,
    'no-obj-calls': 2,
    'no-octal': 2,
    'no-octal-escape': 2,
    'no-path-concat': 2,
    'no-proto': 2,
    'no-redeclare': 2,
    'no-regex-spaces': 2,
    'no-return-assign': [2, 'except-parens'],
    'no-self-assign': 2,
    'no-self-compare': 2,
    'no-sequences': 2,
    'no-shadow-restricted-names': 2,
    'no-spaced-func': 0,
    'no-sparse-arrays': 2,
    'no-this-before-super': 2,
    'no-throw-literal': 2,
    'no-trailing-spaces': 0,
    'no-undef': 2,
    'no-undef-init': 2,
    'no-unexpected-multiline': 2,
    'no-unmodified-loop-condition': 2,
    'no-unneeded-ternary': [2, {
      'defaultAssignment': false
    }],
    'no-unreachable': 2,
    'no-unsafe-finally': 2,
    'no-unused-vars': [0, {
      'vars': 'local',
      'args': 'none'
    }],
    'no-useless-call': 2,
    'no-useless-computed-key': 2,
    'no-useless-constructor': 2,
    'no-useless-escape': 0,
    'no-whitespace-before-property': 0,
    'no-with': 2,
    'one-var': [2, {
      'initialized': 'never'
    }],
/*     'operator-linebreak': [0, 'after', {
      'overrides': {
        '?': 'before',
        ':': 'before'
      }
    }], */
    'padded-blocks': [0, 'never'],
    'quotes': [0, 'single', {
      'avoidEscape': true,
      'allowTemplateLiterals': true
    }],
    'semi': [0, 'never'],
    'semi-spacing': [0, {
      'before': false,
      'after': true
    }],
    'space-before-blocks': [0, 'always'],
    'space-before-function-paren': [0, 'never'],
    'space-in-parens': "off",
    'space-infix-ops': 0,
    'space-unary-ops': [2, {
      'words': true,
      'nonwords': false
    }],
    'spaced-comment': [0, 'always', {
      'markers': ['global', 'globals', 'eslint', 'eslint-disable', '*package', '!', ',']
    }],
    'template-curly-spacing': [2, 'never'],
    'use-isnan': 2,
    'valid-typeof': 2,
    'wrap-iife': [2, 'any'],
    'yield-star-spacing': [2, 'both'],
    'yoda': [0, 'never'],
    'prefer-const': 0,
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
    'object-curly-spacing': [0, 'always', {
      objectsInObjects: false
    }],
    'array-bracket-spacing': [0, 'never'],
    'vue/no-use-v-if-with-v-for': "off",
    "vue/no-unused-components": "off",
    "vue/no-unused-vars":"off",
    "no-prototype-builtins":"off",
    "vue/html-closing-bracket-spacing":"off",
    "vue/html-indent":"off",
    "vue/max-attributes-per-line":"off",
    "vue/valid-v-for":"off",
    "vue/require-valid-default-prop":"off",
    "vue/attributes-order":"off",
    "vue/first-attribute-linebreak":"off",
    "vue/no-parsing-error":"off",
    "vue/attribute-hyphenation":"off"
  }
}
