/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-10-17 16:43:55 UTC)
 * on 2016-12-28 at 10:36:37 UTC 
 * Modify at your own risk.
 */

package com.example.gkhera.myapplication.backend.comedyBox.model;

/**
 * Model definition for Comedy.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the comedyBox. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Comedy extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String artist;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String overview;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("poster_path")
  private java.lang.String posterPath;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("release_date")
  private java.lang.String releaseDate;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getArtist() {
    return artist;
  }

  /**
   * @param artist artist or {@code null} for none
   */
  public Comedy setArtist(java.lang.String artist) {
    this.artist = artist;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Comedy setId(java.lang.Integer id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOverview() {
    return overview;
  }

  /**
   * @param overview overview or {@code null} for none
   */
  public Comedy setOverview(java.lang.String overview) {
    this.overview = overview;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPosterPath() {
    return posterPath;
  }

  /**
   * @param posterPath posterPath or {@code null} for none
   */
  public Comedy setPosterPath(java.lang.String posterPath) {
    this.posterPath = posterPath;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getReleaseDate() {
    return releaseDate;
  }

  /**
   * @param releaseDate releaseDate or {@code null} for none
   */
  public Comedy setReleaseDate(java.lang.String releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  @Override
  public Comedy set(String fieldName, Object value) {
    return (Comedy) super.set(fieldName, value);
  }

  @Override
  public Comedy clone() {
    return (Comedy) super.clone();
  }

}
