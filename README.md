![](https://clojars.org/com.github.bradb/fen/latest-version.svg)

## fen - Clojure library for parsing FEN notation

[FEN notation](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation) is a simple, standard notation for describing a position on a chess board. It includes all the necessary information to start a game from a position, including pawn and piece placement, whose turn it is, castling rights, the current en passant square (if any), move number, and the halfmove clock used to track the [fifty-move rule](https://en.wikipedia.org/wiki/Fifty-move_rule).

## Installing

## Usage

Converting from FEN to a Clojure map:

```clj
(fen->map "4k3/8/8/8/8/8/4P3/4K3 w - - 5 39")
=> #:fen{:fullmove-number 39,
         :en-passant-target-square nil,
         :allow-black-kingside-castle? false,
         :board
         (\- \- \- \- \k \- \- \-
         \- \- \- \- \- \- \- \-
         \- \- \- \- \- \- \- \-
         \- \- \- \- \- \- \- \-
         \- \- \- \- \- \- \- \-
         \- \- \- \- \- \- \- \-
         \- \- \- \- \P \- \- \-
         \- \- \- \- \K \- \- \-),
         :side-to-move :white,
         :halfmove-clock 5,
         :allow-white-queenside-castle? false,
         :allow-black-queenside-castle? false,
         :allow-white-kingside-castle? false}
```

Converting from a Clojure map to FEN:

```clj
(map->fen {:fen/board [\r \n \b \q \k \b \n \r
                       \p \p \p \p \p \p \p \p
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \P \P \P \P \P \P \P \P
                       \R \N \B \Q \K \B \N \R]
           :fen/side-to-move :white
           :fen/fullmove-number 1
           :fen/halfmove-clock 0
           :fen/allow-white-kingside-castle? true
           :fen/allow-white-queenside-castle? true
           :fen/allow-black-kingside-castle? true
           :fen/allow-black-queenside-castle? true
           :fen/en-passant-target-square nil})
=> "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
```

Comments, feedback, code reviews are welcome. Enjoy!
