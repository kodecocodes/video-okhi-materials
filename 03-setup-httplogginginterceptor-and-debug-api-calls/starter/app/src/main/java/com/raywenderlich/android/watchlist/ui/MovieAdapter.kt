/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.watchlist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raywenderlich.android.watchlist.model.MovieModel
import com.raywenderlich.android.watchlist.RecyclerViewItemClickListener
import com.raywenderlich.android.watchlist.Utils
import com.raywenderlich.android.watchlist.databinding.RowMovieViewholderBinding

class MovieAdapter(
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  private val movieList = mutableListOf<MovieModel>()

  fun setCharacterList(characterList: List<MovieModel>) {
    this.movieList.clear()
    this.movieList.addAll(characterList)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val rowMovieViewholderBinding = RowMovieViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieViewHolder(rowMovieViewholderBinding)
  }

  override fun getItemCount() = movieList.size

  @SuppressLint("DefaultLocale")
  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = movieList[position]
    with(holder) {
      Glide.with(moviePosterImageView)
          .load(Utils.getPostUrl(movie.posterPath))
          .centerCrop()
          .into(moviePosterImageView)

      movieNameTextView.text = movie.title

      rootView.setOnClickListener {
        recyclerViewItemClickListener.onItemClicked(movie.id)
      }
    }
  }

  inner class MovieViewHolder(rowMovieViewholderBinding: RowMovieViewholderBinding) : RecyclerView.ViewHolder(rowMovieViewholderBinding.root) {
    val movieNameTextView = rowMovieViewholderBinding.movieNameTextview
    val moviePosterImageView = rowMovieViewholderBinding.moviePosterImageview
    val rootView = rowMovieViewholderBinding.root
  }
}