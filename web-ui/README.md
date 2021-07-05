# kakaopay (web-ui)

> 카카오페이 웹(풀스택) 개발 과제 - FrontEnd

## Dependencies

| No  |  Dependency    | Version  |
|:---:|----------------|----------|
|  1  | vue            | 2.6.11   |
|  2  | vuex           | 3.4.0    |
|  3  | axios          | 0.21.1   |
|  4  | vue-sse        | 2.2.0    |
|  5  | vue2-dropzone  | 3.6.0    |
|  6  | @coreui/coreui | 3.4.0    |

## Run in Development

```
$ yarn install
$ yarn serve
```

## Folder Structure

***

```sh
web-ui
├── src
│   ├── assets
│   │     └── scss
│   │         ├── coreui
    │         └── style.scss
│   ├── compoments            
│   ├── plugins                
│   │     ├── coreui
│   │     ├── dropzone
│   │     ├── sse
│   │     └── index.js
│   ├── store                 
│   │     └── index.js
│   ├── App.vue
│   ├── main.js
│   └── ...
├── ...
├── .env
├── package.json
├── REDADME.md
└── vue.config.js
```