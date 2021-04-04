const path = require("path");
const { VueLoaderPlugin } = require("vue-loader");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
// const HtmlWebpackPlugin = require('html-webpack-plugin')

const PATHS = {
  source: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
  output: path.join(__dirname, '../../../build/resources/static'),
  content: path.join(__dirname, 'src', 'main', 'resources', 'src')
};

module.exports = (env = {}) => ({
  mode: "development",
  devtool: "cheap-module-eval-source-map",
  entry: [
    path.join(PATHS.source, 'main.js'),
    require.resolve(`webpack-dev-server/client`),
  ],
  // output: {
  //   path: PATHS.output,
  //   publicPath: '',
  //   filename: 'bundle.js'
  // },
  resolve: {
    alias: {
      // this isn't technically needed, since the default `vue` entry for bundlers
      // is a simple `export * from '@vue/runtime-dom`. However having this
      // extra re-export somehow causes webpack to always invalidate the module
      // on the first HMR update and causes the page to reload.
      vue: "@vue/runtime-dom"
    },
    modules: [
        path.join(PATHS.source),
        path.join(__dirname, 'node_modules'),
        path.join(PATHS.content),
        path.join(PATHS.content, 'assets'),
        path.join(PATHS.content, 'components'),
        path.join(PATHS.content, 'layouts'),
        path.join(PATHS.content, 'views')
    ]
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env']
          }
        }
      },
      {
        test: /\.vue$/,
        use: "vue-loader"
      },
      {
        test: /\.png$/,
        use: {
          loader: "url-loader",
          options: { limit: 8192 }
        }
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: { hmr: !env.prod }
          },
          "css-loader"
        ]
      },
      {
        test: /\.woff2?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        use: 'url-loader?limit=10000',
      },
      {
        test: /\.(ttf|eot|svg)(\?[\s\S]+)?$/,
        use: 'file-loader',
      },
      {
        test: /\.(jpe?g|png|gif|svg)$/i,
        use: [
          'file-loader?name=images/[name].[ext]',
          'image-webpack-loader?bypassOnDebug'
        ]
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new MiniCssExtractPlugin({
      filename: "[name].css"
    }),
    // new HtmlWebpackPlugin({
    //   template: __dirname + '/src/main/resources/templates/index.html'
    // })
  ],
  devServer: {
    inline: true,
    hot: true,
    stats: "minimal",
    contentBase: './dist',
    overlay: true,
    injectClient: false,
    disableHostCheck: true,
    compress: true,
    port: 8001,
    writeToDisk: true,
    allowedHosts: [
      'localhost:8080'
    ]
  }
});
